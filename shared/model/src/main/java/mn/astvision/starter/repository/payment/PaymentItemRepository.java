package mn.astvision.starter.repository.payment;

import mn.astvision.starter.model.payment.PaymentItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentItemRepository extends MongoRepository<PaymentItem, String> {

    @Query("{ 'paymentNumber': ?0, 'deleted': false }")
    List<PaymentItem> findByPaymentNumber(String paymentNumber);

    @Query("{ 'paymentNumber': ?0, 'passportNumber': ?1, 'deleted': false }")
    List<PaymentItem> findByPaymentNumberAndPassportNumber(String paymentNumber, String passportNumber);
}
