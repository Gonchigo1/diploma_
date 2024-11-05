package mn.astvision.starter.model.paymentinvoice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Төлбөрийн нэхэмжлэхийн төрөл
 * @author digz6666
 */
@Getter
@AllArgsConstructor
public enum PaymentInvoiceType {

    SERVICE_REQUEST, // үйлчилгээний хүсэлт
    ;

    public static PaymentInvoiceType fromString(String input) {
        PaymentInvoiceType paymentInvoiceType = null;
        try {
            paymentInvoiceType = PaymentInvoiceType.valueOf(input);
        } catch (NullPointerException | IllegalArgumentException e) {
        }
        return paymentInvoiceType;
    }
}
