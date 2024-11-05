package mn.astvision.starter.ses.complaint;

import lombok.Data;

import java.util.List;

/**
 * AWS SES-с мэйл complaint хийх үед үүсэх notification-ий доторх complaint-тай холбоотой дата
 * @author digz6666
 */
@Data
public class ComplaintData {

    private String feedbackId; // 010e0188be441832-5e37f1d8-1544-4c9c-b104-3c9042fb3331-000000
    private String complaintSubType; // null
    private String complaintFeedbackType; // abuse
    private List<ComplainedRecipientData> complainedRecipients;
    private String timestamp; // 2023-06-15T08:57:24.000Z
    private String userAgent; // Amazon SES Mailbox Simulator
    private String arrivalDate; // 2023-06-15T08:55:13.876Z
}
