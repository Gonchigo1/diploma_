package mn.astvision.starter.model.systemconfig.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author Tergel
 */
public enum SystemCronType {

    // push notification
    PUSH_NOTIFICATION_SEND,

    // email request
    EMAIL_SEND,

    // payment
    PAYMENT_CONFIRM, // payment баталгаажуулах

    // transaction fiat
    STATEMENT_KHAN, // хаан банкны дансны хуулга болон дансны үлдэгдэл авах
    STATEMENT_GOLOMT, // голомт банкны дансны хуулга болон дансны үлдэгдэл авах
    STATEMENT_TDB, // худалдаа хөгжлийн банкны дансны хуулга болон дансны үлдэгдэл авах

    // fiat transaction
    DEPOSIT_FIAT_CONFIRM, // валютын орлогыг баталгаажуулах /MNT одоохондоо/
    WITHDRAW_FIAT_CONFIRM, // валютын зарлагыг баталгаажуулах /MNT одоохондоо/
    WITHDRAW_FIAT_KHAN, // хаан банкаар шилжүүлэг хийх
    WITHDRAW_FIAT_GOLOMT, // голомт банкаар шилжүүлэг хийх
    WITHDRAW_FIAT_TDB, // голомт банкаар шилжүүлэг хийх
    ;

    public static List<SystemCronType> getPaymentCronTypes() {
        return Arrays.asList(
                STATEMENT_KHAN,
                WITHDRAW_FIAT_KHAN,
                DEPOSIT_FIAT_CONFIRM,
                WITHDRAW_FIAT_CONFIRM,
                PAYMENT_CONFIRM
        );
    }

    public static List<SystemCronType> getPaymentGolomtCronTypes() {
        return Arrays.asList(
                STATEMENT_GOLOMT,
                WITHDRAW_FIAT_GOLOMT
        );
    }

    public static List<SystemCronType> getPaymentTdbCronTypes() {
        return Arrays.asList(
                STATEMENT_TDB,
                WITHDRAW_FIAT_TDB
        );
    }

    public static List<SystemCronType> getSharedCronTypes() {
        return Arrays.asList(
                PUSH_NOTIFICATION_SEND,
                EMAIL_SEND
        );
    }

    public static SystemCronType fromString(String value) {
        try {
            return SystemCronType.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
