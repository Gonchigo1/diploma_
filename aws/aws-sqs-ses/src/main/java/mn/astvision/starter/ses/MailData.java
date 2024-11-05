package mn.astvision.starter.ses;

import lombok.Data;

/**
 * AWS SES-с мэйл bounce, complain хийх үед үүсэх notification доторх и-мэйл дата
 * @author digz6666
 */
@Data
public class MailData {

    private String timestamp; // 2023-06-15T08:57:23.717Z
    private String source; // no-reply@telcocom.mn
    private String sourceArn; // arn:aws:ses:ap-southeast-1:828408407733:identity/telcocom.mn
    private String sourceIp; // 202.131.244.122
    private String callerIdentity; // root
    private String sendingAccountId; // 828408407733
    private String messageId; // 010e0188be461405-3c4d9bf2-0de5-45b5-b952-677f6a09aee6-000000
    private String[] destination; // ["bounce@simulator.amazonses.com"]
    private Boolean headersTruncated;
    private MailHeaderData[] headers;
    private MailCommonHeaderData commonHeaders;
}
