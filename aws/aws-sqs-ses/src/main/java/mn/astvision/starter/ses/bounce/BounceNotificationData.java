package mn.astvision.starter.ses.bounce;

import lombok.Data;
import mn.astvision.starter.ses.MailData;

/**
 * AWS SES-с мэйл bounce хийх үед үүсэх notification-ий дата
 * @author digz6666
 */
@Data
public class BounceNotificationData {

    private String notificationType; // Bounce, AmazonSnsSubscriptionSucceeded
    private BounceData bounce;
    private MailData mail;
}
