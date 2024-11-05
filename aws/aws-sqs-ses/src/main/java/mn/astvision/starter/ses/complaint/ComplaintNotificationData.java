package mn.astvision.starter.ses.complaint;

import lombok.Data;
import mn.astvision.starter.ses.MailData;

/**
 * AWS SES-с мэйл complaint хийх үед үүсэх notification-ий дата
 * @author digz6666
 */
@Data
public class ComplaintNotificationData {

    private String notificationType; // Complaint
    private ComplaintData complaint;
    private MailData mail;
}
