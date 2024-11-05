package mn.astvision.starter.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.exception.notification.NotificationException;
import mn.astvision.starter.model.notification.Notification;
import mn.astvision.starter.model.notification.enums.NotificationStatus;
import mn.astvision.starter.repository.notification.NotificationRepository;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author digz6666
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MongoTemplate mongoTemplate;
    private final NotificationRepository notificationRepository;

    public Notification get(String id) throws NotificationException {
        Notification notification = notificationRepository.findByIdAndDeletedFalse(id);
        if (notification == null)
            throw new NotificationException("Өгөгдөл олдсонгүй: " + id);

        return notification;
    }

    public Notification read(String notifId, String userId) throws NotificationException {
        Notification notification = notificationRepository.findByIdAndDeletedFalse(notifId);
        if (notification == null || !notification.getUserId().equals(userId))
            throw new NotificationException("Өгөгдөл олдсонгүй");

        if (notification.getStatus().equals(NotificationStatus.UNREAD))
            throw new NotificationException("Мэдэгдлийн төлөв буруу байна");

        Map<NotificationStatus, LocalDateTime> statusMap = new HashMap<>();
        if (!ObjectUtils.isEmpty(notification.getStatusDate()))
            statusMap = notification.getStatusDate();

        notification = mongoTemplate.findAndModify(
                new Query()
                        .addCriteria(Criteria.where("id").is(notifId)),
                new Update()
                        .set("status", NotificationStatus.READ)
                        .set("statusDate", statusMap.put(NotificationStatus.READ, LocalDateTime.now()))
                        .set("modifiedDate", LocalDateTime.now()),
                FindAndModifyOptions.options().returnNew(true),
                Notification.class);

        if (notification == null)
            throw new NotificationException("Өгөгдөл хадгалах үед алдаа гарлаа");

        return notification;
    }

    public void clean(String userId) {
        List<Notification> readNotifications = notificationRepository.findAllByUserIdAndStatus(userId,
                NotificationStatus.READ.toString());

        for (Notification notification : readNotifications) {
            Map<NotificationStatus, LocalDateTime> statusMap = new HashMap<>();
            if (!ObjectUtils.isEmpty(notification.getStatusDate()))
                statusMap = notification.getStatusDate();

            mongoTemplate.findAndModify(
                    new Query()
                            .addCriteria(Criteria.where("id").is(notification.getId())),
                    new Update()
                            .set("deleted", true)
                            .set("status", NotificationStatus.DELETED)
                            .set("statusDate", statusMap.put(NotificationStatus.DELETED, LocalDateTime.now()))
                            .set("modifiedDate", LocalDateTime.now()),
                    FindAndModifyOptions.options().returnNew(true),
                    Notification.class);
        }
    }
}
