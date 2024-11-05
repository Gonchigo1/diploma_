package mn.astvision.starter.model.servicefee;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.bank.BankAccount;
import mn.astvision.starter.model.payment.enums.CurrencySymbol;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.math.BigDecimal;

/**
 * Үйлчилгээний төлбөрийн хэмжээ (тариф)
 * 1. Тэмдэгтийн хураамж
 * 2. Үйлчилгээний хураамж
 * @author digz6666
 */
@Sharded(shardKey = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceFee extends BaseEntityWithUser {

    @NotNull(message = "Үйлчилгээний ID оруулна уу")
    private String serviceId; // үйлчилгээний ID

    @NotEmpty(message = "Нэршил оруулна уу")
    private String name; // нэршил

    @NotNull(message = "Банкны дансны ID оруулна уу")
    private String bankAccountId; // банкны дансны ID

    @NotNull(message = "Валют оруулна уу")
    private CurrencySymbol currency; // валют (MNT, USD)

    /**
     * 1. Тэмдэгтийн хураамж
     * 2. Үйлчилгээний хураамж
     */
    private BigDecimal amount; // статик төлбөрийн хэмжээ (USD байвал Монгол банкны албан ханшаар бодно)

    @NotEmpty(message = "Тайлбар оруулна уу")
    private String description; // тайлбар

    @AssertTrue(message = "Төлбөрийн хэмжээ оруулна уу")
    @Transient
    private boolean isAmountOk() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    @Transient
    private BankAccount bankAccount;
}
