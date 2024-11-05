package mn.astvision.starter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dto.EmailQueueDto;
import mn.astvision.starter.exception.email.DuplicateEmailException;
import mn.astvision.starter.model.email.EmailQueue;
import mn.astvision.starter.model.email.EmailQueueCheck;
import mn.astvision.starter.repository.email.EmailQueueCheckRepository;
import mn.astvision.starter.repository.email.EmailQueueRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailQueueService {

    @Value("${spring.mail.from}")
    private String MAIL_FROM;

    @Value("${spring.mail.name}")
    private String MAIL_FROM_NAME;

    private final EmailQueueCheckRepository emailQueueCheckRepository;
    private final EmailQueueRepository emailQueueRepository;
    private final MongoTemplate mongoTemplate;

    @Transactional
    public void addWithCheck(
            String dataType,
            String dataId,
            String to,
            String subject,
            String content,
            List<String> attachments
    ) throws DuplicateEmailException {
        try {
            EmailQueueCheck emailQueueCheck = EmailQueueCheck.builder()
                    .dataType(dataType)
                    .dataId(dataId)
                    .email(to.toLowerCase())
                    .build();
            emailQueueCheck.setCreatedDate(LocalDateTime.now());
            emailQueueCheckRepository.save(emailQueueCheck);
        } catch (Exception e) {
            throw new DuplicateEmailException("Email queue exists: " + dataType + " - " + dataId);
        }

        add(to, subject, content, attachments);
    }

    @Transactional
    public void add(
            String to,
            String subject,
            String content,
            List<String> attachments) {
        log.debug("Adding email to queue: {to: " + to + ", subject: " + subject + "}");

        EmailQueue emailQueue = EmailQueue.builder()
                .from(MAIL_FROM)
                .fromName(MAIL_FROM_NAME)
                .to(to.toLowerCase())
                .subject(subject)
                .content(content)
                .attachments(attachments)
                .build();
        emailQueue.setCreatedDate(LocalDateTime.now());
        emailQueueRepository.save(emailQueue);
    }

    public List<EmailQueue> listForSend(int count) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("sent").is(null)),
                group("to")
                        .first("_id").as("originalId")
                        .first("to").as("email")
//                        .first(EmailQueue.Fields.from).as(EmailQueue.Fields.from)
//                        .first(EmailQueue.Fields.fromName).as(EmailQueue.Fields.fromName)
//                        .first(EmailQueue.Fields.to).as(EmailQueue.Fields.to)
//                        .first(EmailQueue.Fields.subject).as(EmailQueue.Fields.subject)
//                        .first(EmailQueue.Fields.content).as(EmailQueue.Fields.content)
//                        .first(EmailQueue.Fields.attachments).as(EmailQueue.Fields.attachments)
//                        .first(EmailQueue.Fields.sent).as(EmailQueue.Fields.sent)
//                        .first(EmailQueue.Fields.sentDate).as(EmailQueue.Fields.sentDate)
//                        .first(EmailQueue.Fields.errorMessage).as(EmailQueue.Fields.errorMessage)
//                        .first(EmailQueue.Fields.tryCount).as(EmailQueue.Fields.tryCount)
                ,
                limit(count)
        );

        List<EmailQueueDto> queueDtoList = mongoTemplate
                .aggregate(aggregation, "emailQueue", EmailQueueDto.class)
                .getMappedResults();

        List<String> emailQueueIds = queueDtoList.stream()
                .map(EmailQueueDto::getOriginalId)
                .collect(Collectors.toList());
        return emailQueueRepository.findByIdIn(emailQueueIds);
    }
}
