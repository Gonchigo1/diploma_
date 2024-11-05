package mn.astvision.starter.repository.systemconfig;

import mn.astvision.starter.model.systemconfig.SystemKeyValue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MethoD
 */
@Repository
public interface SystemKeyValueRepository extends MongoRepository<SystemKeyValue, String> {

    boolean existsByKey(String key);

    SystemKeyValue findByKey(String key);
}
