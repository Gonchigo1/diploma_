package mn.astvision.starter.model.payment;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.IdentityType;
import mn.astvision.starter.model.payment.enums.PaymentMethodType;
import mn.astvision.starter.model.payment.enums.PaymentStatus;
import mn.astvision.starter.model.payment.enums.PaymentType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Төлбөр
 *
 * @author digz6666
 */
@Sharded(shardKey = {"number"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class Payment extends BaseEntityWithUser {

    private String number; // төлбөрийн дугаар

    private PaymentType paymentType; // төлбөрийн төрөл
    private PaymentMethodType methodType; // төлбөр төлөх хэлбэр
    private String externalOrderId; // бусад төлбөрийн систем дээрх төлбөрийн дугаар
    private PaymentStatus status; // төлөв

    // paymentType == SERVICE_REQUEST
    private String serviceId; // үйлчилгээний ID
    private String serviceRequestId; // үйлчилгээний хүсэлтийн ID

    // paymentType == INVOICE буюу төлбөрийн нэхэмжлэхийг төлсөн бол
    private List<String> invoiceNumbers;

    private String invoiceName; // төлбөрийн нэхэмжлэхийн нэр
    private String invoiceDescription; // төлбөрийн нэхэмжлэхийн тайлбар
    private BigDecimal totalAmount; // нийт дүн
    private BigDecimal totalVat; // нийт НӨАТ дүн
    private BigDecimal totalFee; // шимтгэл (хэрэглэгчээс нэмж суутгах төлбөрийн хэрэгслийн шимтгэл)

    private LocalDateTime paidDate; // төлсөн огноо
    private Boolean verifyResult; // verify үр дүн
    private String verifyMessage; // verify мессеж
    private int verifyTryCount; // verify дуудсан оролдлогын тоо

    private Boolean isUrgent; // Яаралтай эсэх

    /**
     * cron-аар нөхөж verify хийсэн бол бөглөнө
     * төлбөрийн мэдээллийг cron-оор verify хийсэн огноо
     */
    private LocalDateTime cronVerifiedDate;

    /**
     * төлбөр төлж буй иргэн, хуулийн этгээдийн мэдээлэл
     */
    private IdentityType payerType; // citizen, legalEntity
    private String payerId; // төлбөр төлж буй Монгол иргэний регистрийн дугаар эсвэл хэрэглэгчийн ID
    private String legalEntityNumber; // төлбөр төлж буй хуулийн этгээдийн регистрийн дугаар

    /**
     * transient талбарууд
     */
    @Transient
    private List<PaymentItem> items; // төлбөрийн задаргаа
    @Transient
    private PaymentAdditionalData additionalData;

    @Transient
    private String methodTypeName;

    @Transient
    private String paymentTypeName;

    @Transient
    private String statusName;

    @Transient
    private String userEmail;

    @Transient
    private String userFullName;

    @Transient
    public BigDecimal getGrandTotal() {
        BigDecimal grandTotal = BigDecimal.ZERO;
        if (totalAmount != null)
            grandTotal = grandTotal.add(totalAmount);
        if (totalVat != null)
            grandTotal = grandTotal.add(totalVat);
        if (totalFee != null)
            grandTotal = grandTotal.add(totalFee);

        return grandTotal;
    }
}
