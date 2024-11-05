package mn.astvision.starter.repository.payment;

import com.mongodb.lang.Nullable;
import mn.astvision.starter.model.payment.PaymentCancelData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentCancelDataRepository extends MongoRepository<PaymentCancelData, String> {

    @Nullable
    PaymentCancelData findOneByNumber(String number);

    @Query("{ 'methodType': 'WECHAT_PAY', 'cancelResult': {$ne: true}, 'status': 'NEW'," +
           " 'cancelTryCount': {$lt: 3}, 'createdDate': {$lt: ?0} }")
    List<PaymentCancelData> findExpiredInvoiceWechatPay(LocalDateTime date, Pageable pageable);
}
