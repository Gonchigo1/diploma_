package mn.astvision.starter.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.BaseEntityWithoutId;
import mn.astvision.starter.model.email.EmailSuppress;
import mn.astvision.starter.model.email.EmailSuppressType;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author digz6666
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSuppressService {

    private final MongoTemplate mongoTemplate;

    public EmailSuppress create(String email, EmailSuppressType suppressType) {
        return mongoTemplate.findAndModify(
                new Query()
                        .addCriteria(Criteria.where(EmailSuppress.Fields.email).is(email.toLowerCase())),
                new Update()
                        .set(EmailSuppress.Fields.suppressType, suppressType)
                        .set(EmailSuppress.Fields.active, true)
                        .set(BaseEntityWithoutId.Fields.modifiedDate, LocalDateTime.now()),
                FindAndModifyOptions.options()
                        .upsert(true)
                        .returnNew(true),
                EmailSuppress.class
        );
    }
}
