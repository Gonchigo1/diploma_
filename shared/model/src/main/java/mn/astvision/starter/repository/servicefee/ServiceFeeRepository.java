package mn.astvision.starter.repository.servicefee;

import mn.astvision.starter.model.servicefee.ServiceFee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceFeeRepository extends MongoRepository<ServiceFee, String> {

    List<ServiceFee> findByServiceIdAndDeletedFalse(String serviceId);
    List<ServiceFee> findByServiceIdAndDeletedFalseOrderByCreatedDateDesc(String serviceId);
    List<ServiceFee> findByDeletedFalse();
}
