package mn.astvision.starter.model.notification;

import lombok.*;
import lombok.Builder.Default;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.notification.enums.NotificationRelatedDataType;
import mn.astvision.starter.model.notification.enums.NotificationStatus;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.LocalDateTime;
import java.util.Map;

@Sharded(shardKey = {"id"})
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntityWithUser {

    private String userId;
    private NotificationRelatedDataType relatedDataType;
    private String relatedDataId;

    private String title;
    private String message;

    private String link;

    @Default
    private NotificationStatus status = NotificationStatus.UNREAD;
    private Map<NotificationStatus, LocalDateTime> statusDate;
}
