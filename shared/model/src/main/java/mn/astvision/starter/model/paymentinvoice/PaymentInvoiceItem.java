package mn.astvision.starter.model.paymentinvoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Төлбөрийн нэхэмжлэхийн задаргаа
 * @author digz6666
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInvoiceItem {

    private String description; // тайлбар
    private BigDecimal amount; // дүн
    private BigDecimal vat; // НӨАТ дүн

    private String bankCode; // банкны код (Монгол банкнаас олгосон 6 оронтой)
    private String accountNumber; // дансны дугаар
    private String accountName; // дансны нэр
}
