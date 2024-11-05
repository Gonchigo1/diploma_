package mn.astvision.starter.repository;

import mn.astvision.starter.model.MultiLanguage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Odko
 */
@Repository
public interface MultiLanguageRepository extends MongoRepository<MultiLanguage, String> {
}
