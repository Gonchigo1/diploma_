package mn.astvision.starter.model.payment;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.constants.GolomtBankFormat;
import mn.astvision.starter.model.BaseEntity;
import mn.astvision.starter.model.payment.enums.CurrencySymbol;
import mn.astvision.starter.model.payment.enums.PaymentStatus;
import mn.astvision.starter.model.payment.enums.PaymentType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Sharded;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * Төлбөрийн задаргаа
 *
 * @author digz6666
 */
@Sharded(shardKey = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class PaymentItem extends BaseEntity {

    private String paymentNumber; // төлбөрийн дугаар
    private String registryNumber; // төлбөр төлж буй иргэний регистрийн дугаар

    private PaymentType paymentType; // төлбөрийн төрөл
    private PaymentStatus paymentStatus; // төлбөрийн төлөв

    private String bankCode; // банкны код (Монгол банкнаас олгосон 6 оронтой)
    private String accountNumber; // дансны дугаар
    private String accountName; // дансны нэр
    private CurrencySymbol currency; // валют (ихэвчлэн MNT)

    private String name; // Төлбөрийн нэр
    private String description; // тайлбар
    private BigDecimal amount; // төлбөрийн дүн
    private BigDecimal vat; // НӨАТ дүн
    private BigDecimal fee; // шимтгэл (хэрэглэгчээс нэмж суутгах төлбөрийн хэрэгслийн шимтгэл)

    // INVOICE дата (paymentType == SERVICE_REQUEST)
    private String serviceRequestId; // үйлчилгээний хүсэлтийн ID

    // INVOICE дата (paymentType == INVOICE)
    private String invoiceNumber; // нэхэмжлэхийн дугаар

    @Transient
    private String bankName; // Банкны нэр -> Bank

    @Transient
    public String getBankCodeGolomt() {
        if (ObjectUtils.isEmpty(bankCode))
            return null;
        return bankCode.substring(0, 2);
    }

    @Transient
    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.valueOf(0);
        if (amount != null)
            totalAmount = totalAmount.add(amount);
        if (vat != null)
            totalAmount = totalAmount.add(vat);
        return totalAmount;
    }

    @Transient
    public BigDecimal getTotalAmountWithFee() {
        BigDecimal totalAmount = BigDecimal.valueOf(0);
        if (amount != null)
            totalAmount = totalAmount.add(amount);
        if (vat != null)
            totalAmount = totalAmount.add(vat);
        if (fee != null)
            totalAmount = totalAmount.add(fee);
        return totalAmount;
    }

    @Transient
    public String getAmountText() {
        if (amount == null)
            return null;
        return GolomtBankFormat.AMOUNT_FORMAT.format(amount);
    }

    @Transient
    public String getVatText() {
        if (vat == null)
            return null;
        return GolomtBankFormat.AMOUNT_FORMAT.format(vat);
    }

    @Transient
    public String getFeeText() {
        if (fee == null)
            return null;
        return GolomtBankFormat.AMOUNT_FORMAT.format(fee);
    }

    @Transient
    public String getTotalAmountText() {
        return GolomtBankFormat.AMOUNT_FORMAT.format(getTotalAmount());
    }

    @Transient
    public String getQpayItemCode() {
        if (paymentType == null)
            return null;

        return switch (paymentType) {
            case SERVICE_REQUEST -> serviceRequestId;
            case INVOICE -> invoiceNumber;
            default -> null;
        };
    }
}
