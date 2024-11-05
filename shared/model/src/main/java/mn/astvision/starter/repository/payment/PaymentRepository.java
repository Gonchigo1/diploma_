package mn.astvision.starter.repository.payment;

import com.mongodb.lang.Nullable;
import mn.astvision.starter.model.payment.Payment;
import mn.astvision.starter.model.payment.enums.PaymentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    @Nullable
    Payment findOneByNumber(String number);

    @Nullable
    Payment findOneByServiceRequestIdAndStatusAndDeletedFalse(String number, PaymentStatus status);

    @Nullable
    Payment findTopByServiceRequestIdAndStatusAndDeletedFalse(String number, PaymentStatus status);
}
