package mn.astvision.starter.service;

import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.email.EmailQueue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Disabled
@Slf4j
@SpringBootTest
public class EmailQueueServiceTest {

    @Autowired
    private EmailQueueService emailQueueService;

    @Test
    public void testListForSend() {
        List<EmailQueue> emailQueues = emailQueueService.listForSend(10);
        for (EmailQueue emailQueue : emailQueues) {
            log.info("emailQueue: " + emailQueue.getId()
                    + " - " + emailQueue.getTo());
        }
    }
}
