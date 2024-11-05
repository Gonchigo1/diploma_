package mn.astvision.starter.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.notification.enums.NotificationRelatedDataType;
import mn.astvision.starter.model.notification.enums.NotificationStatus;
import mn.astvision.starter.model.notification.Notification;
import mn.astvision.starter.repository.notification.NotificationRepository;
import mn.astvision.starter.socket.NotificationSocketClient;
import mn.astvision.starter.socket.dto.SocketDataDto;
import org.springframework.stereotype.Service;

/**
 * @author digz6666
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSendService {

    private final NotificationRepository notificationRepository;
    private final NotificationSocketClient notificationSocketClient;

    public void send(
            String user,
            NotificationRelatedDataType type,
            NotificationStatus status,
            String content,
            String event,
            String link
    ) {
        try {
            notificationSocketClient.emit("join-room", SocketDataDto.builder()
                    .authKey(notificationSocketClient.getAuthKey())
                    .room(user)
                    .message("")
                    .build());

            Notification notification = new Notification();
            notification.setUserId(user);
            notification.setRelatedDataType(type);
            notification.setStatus(status);
            notification.setMessage(content);
            notification.setLink(link);
            notificationRepository.save(notification);
            log.debug("Sending notification: " + notification);

            notificationSocketClient.emit(
                    event,
                    SocketDataDto.builder()
                            .authKey(notificationSocketClient.getAuthKey())
                            .room(notification.getUserId())
                            .message(notification)
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to send socket notification/paid/ , error: " + e.getMessage());
        }
    }

//    public void send(ServiceRequest serviceRequestAction) {
//        try {
//            notificationSocketClient.notificationEmit("join-room", SocketDto.builder()
//                    .authKey(notificationSocketClient.getAuthKey())
//                    .room(serviceRequestAction.getCreatedBy())
//                    .message("")
//                    .build());
//
//            Notification notification = new Notification();
//            notification.setUser(serviceRequestAction.getUser());
//            notification.setLink(serviceRequestAction.getLink());
//
//            switch (serviceRequestAction.getStatus()) {
//                case EDIT:
//                    notification.setType(NotificationType.GENERAL);
//                    notification.setContent("Таны " + serviceRequestAction.getNumber() + " дугаартай хүсэлтийг
//                    засварт буцаасан байна");
//                    break;
//                case REJECTED:
//                    notification.setType(NotificationType.GENERAL);
//                    notification.setContent("Таны " + serviceRequestAction.getNumber() + " дугаартай хүсэлтийг
//                    татгалзсан байна");
//                    break;
//                case PAYMENT_PENDING:
//                    notification.setType(NotificationType.PAYMENT);
//                    notification.setContent("Таны " + serviceRequestAction.getNumber() + " дугаартай хүсэлтийг
//                    зөвшөөрсөн байна. Та төлбөрөө төлж баталгаажуулна уу");
//                    break;
//                case COMPLETED:
//                    notification.setType(NotificationType.APPROVAL);
//                    notification.setContent("Таны " + serviceRequestAction.getNumber() + " дугаартай хүсэлтийг
//                    зөвшөөрсөн байна");
//                    break;
//            }
//
//            notificationRepository.save(notification);
//
//            notificationSocketClient.notificationEmit(
//                    "notification",
//                    SocketDto.builder()
//                            .authKey(notificationSocketClient.getAuthKey())
//                            .room(notification.getUser())
//                            .message(notification)
//                            .build()
//            );
//        } catch (Exception e) {
//            log.error("Failed to send socket notification/paid/ , error: " + e.getMessage());
//        }
//    }
}
