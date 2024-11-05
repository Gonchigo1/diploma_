package mn.astvision.starter.ses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SqsEmailDto {

    private String queueName;
    private String receiptHandle;

    private String[] emails;
}
