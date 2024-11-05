package mn.astvision.starter.repository.paymentinvoice;

import com.mongodb.lang.Nullable;
import mn.astvision.starter.model.paymentinvoice.PaymentInvoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentInvoiceRepository extends MongoRepository<PaymentInvoice, String> {

    List<PaymentInvoice> findByNumberIn(List<String> number);
    @Nullable
    PaymentInvoice findOneByNumber(String number);
    void deleteByNumber(String number);
}
