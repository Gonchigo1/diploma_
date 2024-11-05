package mn.astvision.starter.ses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * AWS SES-с мэйл complaint хийх үед үүсэх notification-ий гадуурх wrapper дата
 * @author digz6666
 */
@Data
public class NotificationDataWrapper {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("MessageId")
    private String messageId;

    @JsonProperty("TopicArn")
    private String topicArn;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("SignatureVersion")
    private String signatureVersion;

    @JsonProperty("Signature")
    private String signature;

    @JsonProperty("SigningCertURL")
    private String signingCertURL;

    @JsonProperty("UnsubscribeURL")
    private String unsubscribeURL;
}
