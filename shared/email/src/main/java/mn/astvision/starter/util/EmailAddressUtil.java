package mn.astvision.starter.util;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

/**
 * @author digz6666
 */
public class EmailAddressUtil {

    public static boolean isValid(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

    public static String getUnsubscribeLink(String email) {
        return "https://starter.astvision.mn/mobile-api/api/email/unsubscribe?email=" + email;
    }

    public static String getActivateLink(String code) {
//        log.info("code: " + code);
        return "https://starter.astvision.mn/mobile-api/api/auth/activate?code=" + code;
    }
}
