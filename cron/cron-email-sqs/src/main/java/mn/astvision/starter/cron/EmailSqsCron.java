package mn.astvision.starter.cron;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.email.EmailSuppressType;
import mn.astvision.starter.service.SqsEmailService;
import mn.astvision.starter.service.email.EmailSuppressService;
import mn.astvision.starter.ses.dto.SqsEmailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.exception.SdkException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSqsCron {

    @Value("${aws.sqs.bounceQueue}")
    private String bounceQueueName;

    @Value("${aws.sqs.complaintQueue}")
    private String complaintQueueName;

    private final SqsEmailService sqsEmailService;
    private final EmailSuppressService emailSuppressService;

    @Scheduled(initialDelay = 1000, fixedDelay = 5 * 1000)
    public void processBounces() {
        try {
            List<SqsEmailDto> emailDtoList = sqsEmailService.readEmails(bounceQueueName);
            for (SqsEmailDto sqsEmailDto : emailDtoList) {
                for (String email : sqsEmailDto.getEmails())
                    emailSuppressService.create(email, EmailSuppressType.BOUNCE);

                sqsEmailService.deleteMessage(sqsEmailDto);
            }
        } catch (JsonProcessingException | SdkException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5 * 1000)
    public void processComplaints() {
        try {
            List<SqsEmailDto> emailDtoList = sqsEmailService.readEmails(complaintQueueName);
            for (SqsEmailDto sqsEmailDto : emailDtoList) {
                for (String email : sqsEmailDto.getEmails())
                    emailSuppressService.create(email, EmailSuppressType.COMPLAINT);

                sqsEmailService.deleteMessage(sqsEmailDto);
            }
        } catch (JsonProcessingException | SdkException e) {
            log.error(e.getMessage(), e);
        }
    }
}
