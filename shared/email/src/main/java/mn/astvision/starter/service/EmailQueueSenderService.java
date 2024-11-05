package mn.astvision.starter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.exception.email.EmailException;
import mn.astvision.starter.model.BaseEntity;
import mn.astvision.starter.model.BaseEntityWithoutId;
import mn.astvision.starter.model.email.EmailQueue;
import mn.astvision.starter.model.email.EmailSuppress;
import mn.astvision.starter.repository.email.EmailSuppressRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * emailQueue-с мэйл авч илгээдэг сервис
 * @author digz6666
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailQueueSenderService {

    private final EmailSenderService emailSenderService;
    private final EmailSuppressRepository emailSuppressRepository;
    private final MongoTemplate mongoTemplate;

    public void send(EmailQueue emailQueue) {
        // check if its in suppress list
        EmailSuppress emailSuppress = emailSuppressRepository
                .findByEmailAndActiveTrue(emailQueue.getTo().toLowerCase());
        if (emailSuppress != null) {
            log.warn("Email suppressed: " + emailQueue.getTo());

            mongoTemplate.findAndModify(
                    new Query()
                            .addCriteria(Criteria.where(BaseEntity.Fields.id).is(emailQueue.getId())),
                    new Update()
                            .set(EmailQueue.Fields.sent, false)
                            .inc(EmailQueue.Fields.tryCount, 1)
                            .set(EmailQueue.Fields.errorMessage, "Suppressed: " + emailSuppress.getSuppressType())
                            .set(BaseEntityWithoutId.Fields.modifiedDate, LocalDateTime.now()),
                    EmailQueue.class);
            return;
        }

        List<File> attachments = new ArrayList<>();
        for (String filePath : emailQueue.getAttachments()) {
            // String resolvedFilePath = astFileUtil.resolveFilePath(filePath);
            attachments.add(new File(filePath));
        }

        try {
            emailSenderService.send(
                    emailQueue.getFrom(),
                    emailQueue.getFromName(),
                    emailQueue.getTo(),
                    emailQueue.getSubject(),
                    emailQueue.getContent(),
                    attachments);

            mongoTemplate.findAndModify(
                    new Query()
                            .addCriteria(Criteria.where(BaseEntity.Fields.id).is(emailQueue.getId())),
                    new Update()
                            .set(EmailQueue.Fields.sent, true)
                            .inc(EmailQueue.Fields.tryCount, 1)
                            .set(BaseEntityWithoutId.Fields.modifiedDate, LocalDateTime.now()),
                    EmailQueue.class);
        } catch (EmailException e) {
            log.error(emailQueue.getTo() + " -> " + e.getMessage());

            mongoTemplate.findAndModify(
                    new Query()
                            .addCriteria(Criteria.where(BaseEntity.Fields.id).is(emailQueue.getId())),
                    new Update()
                            .set(EmailQueue.Fields.sent, false)
                            .inc(EmailQueue.Fields.tryCount, 1)
                            .set(EmailQueue.Fields.errorMessage, e.getMessage())
                            .set(BaseEntityWithoutId.Fields.modifiedDate, LocalDateTime.now()),
                    EmailQueue.class);
        }
    }
}
