package mn.astvision.starter.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

import mn.astvision.starter.model.mobile.enums.PushNotificationReceiverType;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.SendResponse;
import com.mongodb.MongoException;

import cyclops.data.Vector;
import cyclops.futurestream.LazyReact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dao.mobile.PushNotificationDao;
import mn.astvision.starter.google.firebase.FirebaseMessagingService;
import mn.astvision.starter.model.mobile.DeviceToken;
import mn.astvision.starter.model.mobile.PushNotification;
import mn.astvision.starter.repository.mobile.DeviceTokenRepository;
import mn.astvision.starter.repository.mobile.PushNotificationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushNotificationService {

    private static final int THREAD_COUNT = 5;

    private final FirebaseMessagingService firebaseMessagingService;
    private final DeviceTokenRepository deviceTokenRepository;
    private final PushNotificationRepository pushNotificationRepository;
    private final PushNotificationDao pushNotificationDAO;

    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

    public void send(PushNotification pushNotification) throws MongoException {
        switch (pushNotification.getReceiverType()) {
            case TOKEN:
            case USERNAME:
                sendOne(pushNotification);
            case ALL:
            default: {
                pushNotificationDAO.setCompleted(pushNotification.getId());
                sendMulti(pushNotification);
            }
        }
    }

    public void send(String pushNotificationId) throws Exception {
        PushNotification pushNotification = pushNotificationRepository.findById(pushNotificationId).orElse(null);
        if (pushNotification != null)
            send(pushNotification);
        else
            throw new Exception("Push notification not found");
    }

    public void sendOne(PushNotification pushNotification) throws MongoException {
        if (pushNotification.getReceiverType() == PushNotificationReceiverType.TOKEN
                || pushNotification.getReceiverType() == PushNotificationReceiverType.USERNAME) {

            switch (pushNotification.getReceiverType()) {
                case USERNAME:
                    List<DeviceToken> deviceTokens = deviceTokenRepository.findByEmail(pushNotification.getReceiver());

                    List<String> tokens = new ArrayList<>();
                    for (DeviceToken deviceToken : deviceTokens)
                        tokens.add(deviceToken.getToken());

                    if (!tokens.isEmpty()) {
                        try {
                            BatchResponse batchResponse = firebaseMessagingService.sendMulti(
                                    tokens,
                                    pushNotification.getType(),
                                    pushNotification.getTitle(),
                                    pushNotification.getBody(),
                                    pushNotification.getData());

                            pushNotification.setSuccessCount(batchResponse.getSuccessCount());
                            pushNotification.setFailureCount(batchResponse.getFailureCount());
                            pushNotification.setSendResult(true);
                            pushNotification.setSentDate(LocalDateTime.now());

                            if (batchResponse.getResponses() != null)
                                for (int i = 0; i < batchResponse.getResponses().size(); i++) {
                                    SendResponse sendResponse = batchResponse.getResponses().get(i);

                                    if (!sendResponse.isSuccessful()) {
                                        if (sendResponse.getException() != null) {
                                            if (sendResponse.getException()
                                                    .getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                                                removeDeviceToken(tokens.get(i));
                                            }
                                        }
                                    }
                                }
                        } catch (IllegalArgumentException | FirebaseMessagingException e) {
                            log.error(e.getMessage(), e);

                            pushNotification.setSendResult(false);
                            pushNotification.setResultMessage(e.getMessage());
                        }
                    } else {
                        pushNotification.setSendResult(false);
                        pushNotification.setResultMessage("Тухайн хэрэглэгчид хамаарах device token олдсонгүй");
                    }

                    pushNotificationRepository.save(pushNotification);
                    break;
                case TOKEN:
                default:
                    DeviceToken deviceToken = deviceTokenRepository
                            .findTop1ByTokenOrderByIdDesc(pushNotification.getReceiver());
                    if (deviceToken != null) {
                        try {
                            firebaseMessagingService.send(
                                    pushNotification.getReceiver(),
                                    pushNotification.getType(),
                                    pushNotification.getTitle(),
                                    pushNotification.getBody(),
                                    pushNotification.getData());

                            pushNotification.setSuccessCount(1);
                            pushNotification.setSendResult(true);
                            pushNotification.setSentDate(LocalDateTime.now());
                        } catch (FirebaseMessagingException e) {
                            log.error(e.getMessage(), e);

                            pushNotification.setFailureCount(1);
                            pushNotification.setSendResult(false);
                            pushNotification.setResultMessage("" + e.getMessagingErrorCode());

                            if (e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED)
                                removeDeviceToken(deviceToken.getToken());
                        }
                    } else {
                        pushNotification.setSendResult(false);
                        pushNotification.setResultMessage("Device token null");
                        log.error(pushNotification.getReceiverType() + " - " + pushNotification.getReceiver()
                                + " -> device token null");
                    }

                    pushNotificationRepository.save(pushNotification);
                    break;
            }
        }
    }

    public void sendMulti(PushNotification pushNotification) throws MongoException {
        if (pushNotification.getReceiverType() == PushNotificationReceiverType.ALL) {
            // log.info("preparing to send: " + pushNotification.getId());
            Stream<DeviceToken> deviceTokens = deviceTokenRepository.findTokensOnly();
            Consumer<Vector<DeviceToken>> consumer = (Vector<DeviceToken> tokenBatch) -> {
                List<String> tokens = tokenBatch.map(DeviceToken::getToken).toList();
                // log.info("tokens: " + tokens.size());
                if (!tokens.isEmpty()) {
                    executorService.submit(new NotificationSendTask(pushNotification, tokens));
                }
            };

            new LazyReact()
                    .fromStream(deviceTokens)
                    .grouped(500)
                    .forEach(consumer);
        }
    }

    class NotificationSendTask implements Callable<Boolean> {
        PushNotification pushNotification;
        List<String> tokens;

        public NotificationSendTask(PushNotification pushNotification, List<String> tokens) {
            this.pushNotification = pushNotification;
            this.tokens = tokens;
        }

        @Override
        public Boolean call() {
            try {
                // log.info("sending to: " + tokens.size());
                try {
                    BatchResponse batchResponse = firebaseMessagingService.sendMulti(
                            tokens,
                            pushNotification.getType(),
                            pushNotification.getTitle(),
                            pushNotification.getBody(),
                            pushNotification.getData());

                    pushNotificationDAO.incBatchCountSent(pushNotification.getId(), batchResponse.getSuccessCount(),
                            batchResponse.getFailureCount());

                    if (batchResponse.getResponses() != null && batchResponse.getResponses().size() == tokens.size()) {
                        for (int i = 0; i < batchResponse.getResponses().size(); i++) {
                            SendResponse sendResponse = batchResponse.getResponses().get(i);

                            if (!sendResponse.isSuccessful()) {
                                if (sendResponse.getException() != null) {
                                    if (sendResponse.getException()
                                            .getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                                        removeDeviceToken(tokens.get(i));
                                    }
                                }
                            }
                        }
                    }
                } catch (IllegalArgumentException | FirebaseMessagingException e) {
                    log.error(e.getMessage(), e);
                }
                return true;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return false;
            }
        }
    }

    private void removeDeviceToken(String token) {
        try {
            log.info("removing device token " + token);
            deviceTokenRepository.deleteByToken(token);
        } catch (MongoException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void removeDeviceToken(String os, String token) {
        try {
            log.info("removing device token " + os + " - " + token);
            deviceTokenRepository.deleteByTokenAndOs(token, os);
        } catch (MongoException e) {
            log.error(e.getMessage(), e);
        }
    }
}
