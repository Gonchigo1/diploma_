package mn.astvision.starter.ses.bounce;

import lombok.Data;

import java.util.List;

/**
 * AWS SES-с мэйл bounce хийх үед үүсэх notification доторх bounce-тай холбоотой дата
 * @author digz6666
 */
@Data
public class BounceData {

    private String feedbackId; // 010e0188be461516-31677098-f0e6-4917-9790-9b66e3c947c3-000000
    private String bounceType; // Permanent
    private String bounceSubType; // General
    private List<BouncedRecipientData> bouncedRecipients;
    private String timestamp; // 2023-06-15T08:57:24.000Z
    private String remoteMtaIp; // 3.1.187.19
    private String reportingMTA; // dns; e232-9.smtp-out.ap-southeast-1.amazonses.com
}
