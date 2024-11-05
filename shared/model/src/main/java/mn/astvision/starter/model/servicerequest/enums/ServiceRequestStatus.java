package mn.astvision.starter.model.servicerequest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Үйлчилгээний хүсэлтийн төлөв
 */
@AllArgsConstructor
@Getter
public enum ServiceRequestStatus {

    NEW("NEW", "Шинэ","#3fa1ff", "plus-circle", "", ""), // шинэ
    DRAFT("DRAFT", "Ноорог", "#874d00", "plus-circle", "", ""), // ноорог
    SUBMITTED("SUBMITTED", "Илгээсэн","#69b1ff", "mail", "", "Илгээсэн"), // илгээсэн
    REASSIGNED("REASSIGNED", "Шилжүүлсэн","#ff9c6e", "mail", "", "Шилжүүлсэн"), // илгээсэн
    OPINION("OPINION", "Санал авч байгаа", "#006d75", "mail", "", "Санал авсан"), // санал авч байгаа
    PROCESSING("PROCESSING", "Шалгаж байгаа", "#3ecbcb", "mail", "Мэргэжилтэн", "Хүлээж авсан"), // шалгаж байгаа
    CHECKING("CHECKING", "Хянаж байгаа", "#fadb14", "mail", "Ахлах", "Шалгасан"), // хянаж байгаа
    APPROVING("APPROVING", "Баталгаажуулж байгаа", "#d3f261", "mail", "Дарга", "Хянасан"), // Баталгаажуулж байгаа
    EDIT("EDIT", "Засварт буцаасан", "#ffa940", "edit", "", "Засварт буцаасан"), // засварт буцаасан
    REJECTED("REJECTED", "Татгалзсан", "#f2637b", "rollback", "", "Татгалзсан"), // өгөхөөс татгалзсан, буцаасан материал дутуу
    COMPLETED("COMPLETED", "Шийдвэрлэсэн", "#73d13d", "check-circle", "", "Шийдвэрлэсэн"), // шийдвэрлэсэн
    APPROVED("APPROVED", "Зөвшөөрсөн", "#73d13d", "check-circle", "", "Зөвшөөрсөн"), // зөвшөөрсөн, үйлчилгээ авсан
    REGISTERED("REGISTERED", "Бүртгэгдсэн", "#69c0ff", "check-circle", "", "Бүртгэгдсэн"), // зөвшөөрсөн, үйлчилгээ авсан
    FAILED("FAILED", "Амжилтгүй", "#ff4d4f", "info-circle", "", ""); // амжилтгүй (төлбөр амжилтгүй)

    final String value;
    final String name;
    final String colorCode;
    final String icon;
    final String position;
    final String action;

    public static List<ServiceRequestStatus> getBackOfficeStatus() {
        return List.of(
                SUBMITTED,
                OPINION,
                PROCESSING,
                CHECKING,
                APPROVING,
                EDIT,
                REJECTED,
                COMPLETED,
                APPROVED
        );
    }

    public static ServiceRequestStatus fromString(String input) {
        ServiceRequestStatus serviceRequestStatus = null;
        try {
            serviceRequestStatus = ServiceRequestStatus.valueOf(input);
        } catch (NullPointerException | IllegalArgumentException ignored) {
        }
        return serviceRequestStatus;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getPosition() {
        return position;
    }
}
