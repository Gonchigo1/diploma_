package mn.astvision.starter.model.notification.enums;

import lombok.Getter;

@Getter
public enum NotificationRelatedDataType {

    USER_2FA("USER_2FA", "Хэрэглэгч нэвтрэлт"),
    USER_PROFILE("USER_PROFILE", "Хувийн мэдээлэл"),
    SERVICE("SERVICE", "Үйлчилгээ"),
    PAYMENT("PAYMENT", "Төлбөр");

    final String value;
    final String name;

    NotificationRelatedDataType(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
