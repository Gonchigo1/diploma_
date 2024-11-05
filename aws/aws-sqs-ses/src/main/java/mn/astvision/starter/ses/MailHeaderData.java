package mn.astvision.starter.ses;

import lombok.Data;

/**
 * AWS SES-с мэйл bounce, complain хийх үед үүсэх notification доторх и-мэйл header дата
 * @author digz6666
 */
@Data
public class MailHeaderData {

    private String name; // MIME-Version
    private String value; // 1.0
}
