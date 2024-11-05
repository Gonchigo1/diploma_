package mn.astvision.starter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.ses.NotificationDataWrapper;
import mn.astvision.starter.ses.bounce.BounceNotificationData;
import mn.astvision.starter.ses.dto.SqsEmailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqsEmailService {

    @Value("${aws.sqs.access-key}")
    private String accessKey;

    @Value("${aws.sqs.secret-key}")
    private String secretKey;

    private SqsClient sqsClient;

    private final ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        try {
            StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider
                    .create(AwsBasicCredentials.create(accessKey, secretKey));

            sqsClient = SqsClient.builder()
                    .region(Region.US_EAST_1)
                    .credentialsProvider(credentialsProvider)
                    .build();
        } catch (SdkException e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<SqsEmailDto> readEmails(String queueName) throws SdkException, JsonProcessingException {
        List<SqsEmailDto> emailDtoList = new ArrayList<>();

        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl(getQueueUrl(queueName))
                .maxNumberOfMessages(10)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();
        if (messages.size() > 0) {
            log.info("Processing bounced emails: " + messages.size());

            for (Message message : messages) {
                log.info(message.body());
                NotificationDataWrapper notificationData = objectMapper
                        .readValue(message.body(), NotificationDataWrapper.class);

                BounceNotificationData bounceNotificationData = objectMapper
                        .readValue(notificationData.getMessage(), BounceNotificationData.class);
                log.info("bounceNotificationData -> " + bounceNotificationData);

                if (bounceNotificationData.getNotificationType().equals("Bounce")) {
                    log.info("Bounce emails -> " + Arrays.toString(bounceNotificationData.getMail().getDestination()));
                    emailDtoList.add(SqsEmailDto.builder()
                            .queueName(queueName)
                            .receiptHandle(message.receiptHandle())
                            .emails(bounceNotificationData.getMail().getDestination())
                            .build());
                }
            }
        }

        return emailDtoList;
    }

    public void deleteMessage(SqsEmailDto sqsEmailDto) throws SdkException {
        sqsClient.deleteMessage(builder -> builder
                .queueUrl(getQueueUrl(sqsEmailDto.getQueueName()))
                .receiptHandle(sqsEmailDto.getReceiptHandle()));

    }

    private String getQueueUrl(String queueName) throws SdkException {
        return sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
                        .queueName(queueName)
                        .build())
                .queueUrl();
    }
}
