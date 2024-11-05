package mn.astvision.starter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.email.EmailSuppressType;
import mn.astvision.starter.service.email.EmailSuppressService;
import mn.astvision.starter.ses.dto.SqsEmailDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Disabled
@Slf4j
@SpringBootTest
public class SqsEmailServiceTest {

    @Autowired
    private EmailSuppressService emailSuppressService;

    @Autowired
    private SqsEmailService sqsEmailService;

    @Test
    public void testProcess() throws JsonProcessingException {
//        log.info("testing process...");
        List<SqsEmailDto> emailDtoList = sqsEmailService.readEmails("starter-bounce");
        for (SqsEmailDto sqsEmailDto : emailDtoList) {
            log.info("sqsEmailDto: {}", sqsEmailDto);

            for (String email : sqsEmailDto.getEmails()) {
                emailSuppressService.create(email, EmailSuppressType.BOUNCE);
            }

//            sqsEmailService.deleteMessage(sqsEmailDto);
        }
    }
}
