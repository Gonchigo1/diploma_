package mn.astvision.starter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.exception.email.EmailException;
import mn.astvision.starter.util.EmailAddressUtil;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author digz6666
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

//    @Autowired(required = false)
//    public void setMailSender(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }

    public void send(
            String from,
            String fromName,
            String to,
            String subject,
            String content,
            List<File> attachments) throws EmailException {
//        log.debug("Sending email: {from: " + from + ", to: " + to + ", subject: " + subject
//                + ", content: " + content + ", attachments: " + attachments + " }");

        if (!EmailAddressUtil.isValid(to))
            throw new EmailException("Invalid email address: " + to);

        try {
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom(from, fromName);
                messageHelper.setTo(to);
                messageHelper.setSubject(subject);
                messageHelper.setText(content, true); // html
                if (attachments != null) {
                    for (File file : attachments) {
                        messageHelper.addAttachment(file.getName(), file);
                    }
                }
            };

            mailSender.send(messagePreparator);
            log.info("Sent email to " + to);
        } catch (MailException e) {
//            log.error(e.getMessage(), e);
            throw new EmailException(e.getMessage());
        }
    }
}
