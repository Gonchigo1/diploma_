package mn.astvision.starter.repository.email;

import mn.astvision.starter.model.email.EmailSuppress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author digz6666
 */
@Repository
public interface EmailSuppressRepository extends MongoRepository<EmailSuppress, String> {

    @Nullable
    EmailSuppress findByEmailAndActiveTrue(String email);
}
