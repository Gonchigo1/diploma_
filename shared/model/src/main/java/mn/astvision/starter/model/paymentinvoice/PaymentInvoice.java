package mn.astvision.starter.model.paymentinvoice;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.payment.enums.CurrencySymbol;
import mn.astvision.starter.model.paymentinvoice.enums.PaymentInvoiceStatus;
import mn.astvision.starter.model.paymentinvoice.enums.PaymentInvoiceType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Төлбөрийн нэхэмжлэх
 * @author digz6666
 */
@Sharded(shardKey = {"number"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class PaymentInvoice extends BaseEntityWithUser {

    private String number; // нэхэмжлэхийн дугаар
    private PaymentInvoiceType type; // нэхэмжлэхийн төрөл

    private CurrencySymbol currency; // валют (ихэвчлэн MNT)
    private List<PaymentInvoiceItem> items; // нэхэмжлэхийн задаргаа
    private BigDecimal totalAmount; // нийт дүн
    private BigDecimal totalVat; // нийт НӨАТ дүн

    /**
     * нэхэмжлэхийн төлөв
     * NEW - шинэ (төлөх боломжгүй)
     * PENDING - хүлээгдэж буй (төлөх боломжтой)
     * PAID - төлсөн
     */
    private PaymentInvoiceStatus status;
    private LocalDateTime paidDate; // нэхэмжлэх төлсөн огноо

    @Transient
    private String typeName;

    @Transient
    private String description;

    @Transient
    public BigDecimal getGrantTotal() {
        BigDecimal grandTotal = BigDecimal.ZERO;
        if (totalAmount != null)
            grandTotal = grandTotal.add(totalAmount);
        if (totalVat != null)
            grandTotal = grandTotal.add(totalVat);

        return grandTotal;
    }
}
