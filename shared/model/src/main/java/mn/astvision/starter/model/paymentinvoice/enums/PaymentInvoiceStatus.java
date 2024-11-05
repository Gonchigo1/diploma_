package mn.astvision.starter.model.paymentinvoice.enums;

/**
 * Төлбөрийн нэхэмжлэхийн төлөв
 * @author digz6666
 */
public enum PaymentInvoiceStatus {

    NEW, // Шинэ
    PENDING, // Хүлээгдэж буй
    PAID; // Төлсөн

    public static PaymentInvoiceStatus fromString(String input) {
        PaymentInvoiceStatus paymentInvoiceStatus = null;
        try {
            paymentInvoiceStatus = PaymentInvoiceStatus.valueOf(input);
        } catch (NullPointerException | IllegalArgumentException e) {
        }
        return paymentInvoiceStatus;
    }
}
