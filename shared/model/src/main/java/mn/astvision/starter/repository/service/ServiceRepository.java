package mn.astvision.starter.repository.service;

import mn.astvision.starter.model.service.Service;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ariuka
 */
@Repository
public interface ServiceRepository extends MongoRepository<Service, String> {

    Service findByIdAndDeletedFalse(String id);
}
