package mn.astvision.starter.ses;

import lombok.Data;

@Data
public class MailCommonHeaderData {

    private String[] from;
    private String[] to;
    private String[] cc;
    private String subject;
}
