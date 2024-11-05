package mn.astvision.starter.model.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Төлбөрийн төлөв
 * @author digz6666
 */
@Getter
@AllArgsConstructor
public enum PaymentStatus {

    NEW("NEW", "Шинэ"), // шинэ
    PAID("PAID", "Төлсөн"), // төлсөн
    UNPAID("UNPAID", "Төлөөгүй"), // төлөөгүй
    RETURNED("RETURNED", "Буцаасан"), // буцаасан (гомдлын дагуу)
    FAILED("FAILED", "Амжилтгүй"), // амжилтгүй болсон
    CANCELLED("CANCELLED", "Цуцалсан"); // цуцласан

    private final String value;
    private final String name;

    public static PaymentStatus fromString(String input) {
        PaymentStatus paymentStatus = null;
        try {
            paymentStatus = PaymentStatus.valueOf(input);
        } catch (NullPointerException | IllegalArgumentException e) {
        }
        return paymentStatus;
    }
}
