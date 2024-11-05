package mn.astvision.starter.ses.complaint;

import lombok.Data;

/**
 * AWS SES-с мэйл complain хийх үед үүсэх notification доторх хүлээн авагчийн дата
 * @author digz6666
 */
@Data
public class ComplainedRecipientData {

    private String emailAddress; // complaint@simulator.amazonses.com
}
