package mn.astvision.starter.repository.email;

import mn.astvision.starter.model.email.EmailQueueCheck;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author digz6666
 */
@Repository
public interface EmailQueueCheckRepository extends MongoRepository<EmailQueueCheck, String> {
}
