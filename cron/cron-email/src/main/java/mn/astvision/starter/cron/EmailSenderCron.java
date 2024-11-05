package mn.astvision.starter.cron;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.email.EmailQueue;
import mn.astvision.starter.service.EmailQueueSenderService;
import mn.astvision.starter.service.EmailQueueService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSenderCron {

//    private final EmailQueueRepository emailQueueRepository;
    private final EmailQueueSenderService emailQueueSenderService;
    private final EmailQueueService emailQueueService;

    // try to send 1000 email every 5 seconds
    @Scheduled(initialDelay = 1000, fixedDelay = 5 * 1000)
    public void send() {
//        List<EmailQueue> emailQueues = emailQueueRepository.findForSend(PageRequest.of(0, 10));
        List<EmailQueue> emailQueues = emailQueueService.listForSend(100);
        if (!emailQueues.isEmpty()) {
            log.debug("Sending emails: " + emailQueues.size());
            for (EmailQueue emailQueue : emailQueues) {
                emailQueueSenderService.send(emailQueue);
            }
        }
    }
}
