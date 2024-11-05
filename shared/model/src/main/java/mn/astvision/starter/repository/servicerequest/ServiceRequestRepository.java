package mn.astvision.starter.repository.servicerequest;

import mn.astvision.starter.model.servicerequest.ServiceRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ariuka
 */
@Repository
public interface ServiceRequestRepository extends MongoRepository<ServiceRequest, String> {

    ServiceRequest findByIdAndDeletedFalse(String id);
    Optional<ServiceRequest> findByNumberAndDeletedFalse(String number);
}
