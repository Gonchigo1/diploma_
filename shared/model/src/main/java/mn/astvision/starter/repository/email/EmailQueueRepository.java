package mn.astvision.starter.repository.email;

import mn.astvision.starter.model.email.EmailQueue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author digz6666
 */
@Repository
public interface EmailQueueRepository extends MongoRepository<EmailQueue, String> {

    @Query("{'sent': null}")
    List<EmailQueue> findForSend(Pageable pageable);

    List<EmailQueue> findByIdIn(List<String> ids);
}
