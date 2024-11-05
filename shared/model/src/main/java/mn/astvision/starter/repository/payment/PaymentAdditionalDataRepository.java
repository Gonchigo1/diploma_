package mn.astvision.starter.repository.payment;

import com.mongodb.lang.Nullable;
import mn.astvision.starter.model.payment.PaymentAdditionalData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentAdditionalDataRepository extends MongoRepository<PaymentAdditionalData, String> {

    boolean existsByNumber(String number);

    @Nullable
    PaymentAdditionalData findOneByNumber(String number);
}
