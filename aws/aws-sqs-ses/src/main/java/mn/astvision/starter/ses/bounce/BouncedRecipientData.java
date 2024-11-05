package mn.astvision.starter.ses.bounce;

import lombok.Data;

/**
 * AWS SES-с мэйл bounce хийх үед үүсэх notification доторх хүлээн авагчийн дата
 * @author digz6666
 */
@Data
public class BouncedRecipientData {

    private String emailAddress; // bounce@simulator.amazonses.com
    private String action; // failed
    private String status; // 5.1.1
    private String diagnosticCode; // smtp; 550 5.1.1 <email>... User unknown
}
