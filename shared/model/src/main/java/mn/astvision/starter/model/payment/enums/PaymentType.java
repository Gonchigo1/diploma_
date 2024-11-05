package mn.astvision.starter.model.payment.enums;

import lombok.Getter;

/**
 * Төлбөрийн төрөл
 * @author digz6666
 */
@Getter
public enum PaymentType {

    SERVICE_REQUEST("SERVICE_REQUEST", "Үйлчилгээний хүсэлт"), // ServiceRequest
    INVOICE("INVOICE", "Нэхэмжлэх"), // PaymentInvoice
    DEMO("DEMO", "Demo") // Demo
    ;

    final String name;
    final String description;

    PaymentType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static PaymentType fromString(String input) {
        PaymentType paymentType = null;
        try {
            paymentType = PaymentType.valueOf(input);
        } catch (NullPointerException | IllegalArgumentException e) {
        }
        return paymentType;
    }
}
