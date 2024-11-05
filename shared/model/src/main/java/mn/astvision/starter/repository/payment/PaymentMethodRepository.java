package mn.astvision.starter.repository.payment;

import com.mongodb.lang.Nullable;
import mn.astvision.starter.model.payment.PaymentMethod;
import mn.astvision.starter.model.payment.enums.PaymentMethodType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends MongoRepository<PaymentMethod, PaymentMethodType> {

    boolean existsByType(PaymentMethodType type);

    @Nullable
    PaymentMethod findByType(PaymentMethodType type);
}
