package mn.astvision.starter.api.mobile;

import com.mongodb.MongoException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.mobile.PushNotificationDao;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.model.mobile.PushNotification;
import mn.astvision.starter.model.mobile.enums.PushNotificationReceiverType;
import mn.astvision.starter.model.mobile.enums.PushNotificationSendType;
import mn.astvision.starter.model.mobile.enums.PushNotificationType;
import mn.astvision.starter.repository.mobile.PushNotificationRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @author digz6666
 */
@Slf4j
@RestController
@RequestMapping("/v1/push-notification")
@Secured({"ROLE_PUSH_NOTIFICATION_VIEW", "ROLE_PUSH_NOTIFICATION_MANAGE"})
@RequiredArgsConstructor
public class PushNotificationApi extends BaseController {

    private final PushNotificationRepository pushNotificationRepository;
    private final PushNotificationDao pushNotificationDAO;

    @GetMapping
    public ResponseEntity<?> list(
            PushNotificationType type,
            PushNotificationSendType sendType,
            PushNotificationReceiverType receiverType,
            String receiver,
            Boolean sendResult,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sentDate1,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sentDate2,
            String sortField,
            String sortOrder,
            AntdPagination pagination
    ) {
        if (Objects.equals(sortOrder, "ascend"))
            pagination.setSortDirection(Sort.Direction.ASC);
        else
            pagination.setSortDirection(Sort.Direction.DESC);

        if (ObjectUtils.isEmpty(sortField))
            pagination.setSortParam("id");
        else
            pagination.setSortParam(sortField);

        AntdTableDataList<PushNotification> listData = new AntdTableDataList<>();
        pagination.setTotal(pushNotificationDAO.count(
                type,
                sendType,
                receiverType,
                receiver,
                sendResult,
                sentDate1,
                sentDate2));
        listData.setPagination(pagination);
        listData.setList(pushNotificationDAO.list(
                type,
                sendType,
                receiverType,
                receiver,
                sendResult,
                sentDate1,
                sentDate2,
                pagination.toPageRequest()));

        return ResponseEntity.ok(listData);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        if (ObjectUtils.isEmpty(id))
            return ResponseEntity.badRequest().body("ID хоосон байна");

        Optional<PushNotification> pushNotificationOpt = pushNotificationRepository.findById(id);
        if (pushNotificationOpt.isEmpty())
            return ResponseEntity.badRequest().body("Push notification олдсонгүй");

        return ResponseEntity.ok(pushNotificationOpt.get());
    }

    @Secured({"ROLE_PUSH_NOTIFICATION_MANAGE"})
    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody PushNotification request, @AuthUser User user) {
        if ((request.getReceiverType() == PushNotificationReceiverType.USERNAME ||
                request.getReceiverType() == PushNotificationReceiverType.TOKEN)
                && ObjectUtils.isEmpty(request.getReceiver())
        )
            return badRequestMessage("Хүлээн авагч хоосон байна");

        try {
            PushNotification pushNotification = new PushNotification();
            pushNotification.setType(request.getType());
            pushNotification.setSendType(request.getSendType());
            pushNotification.setScheduledDate(request.getScheduledDate());
            if (pushNotification.getScheduledDate() == null)
                pushNotification.setScheduledDate(LocalDateTime.now());
            pushNotification.setReceiverType(request.getReceiverType());
            pushNotification.setReceiver(request.getReceiver());
            if (request.getReceiverType() == PushNotificationReceiverType.USERNAME)
                pushNotification.setReceiver(request.getReceiver());
            pushNotification.setPriority(request.getPriority());
            pushNotification.setTitle(request.getTitle());
            pushNotification.setBody(request.getBody());
            pushNotification.setData(request.getData());
            pushNotification.setCreatedDate(LocalDateTime.now());
            pushNotification.setCreatedBy(user.getId());
            pushNotification = pushNotificationRepository.save(pushNotification);

            return ResponseEntity.ok(pushNotification.getId());
        } catch (MongoException | DataAccessException e) {
            log.error(e.getMessage(), e);
            return errorDatabase();
        }
    }

    @Secured({"ROLE_PUSH_NOTIFICATION_MANAGE"})
    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody PushNotification request, @AuthUser User user) {
        if (ObjectUtils.isEmpty(request.getId()))
            return badRequestMessage("ID хоосон байна");

        Optional<PushNotification> pushNotificationOpt = pushNotificationRepository.findById(request.getId());
        if (pushNotificationOpt.isEmpty())
            return badRequestMessage("Push notification олдсонгүй");

        PushNotification pushNotification = pushNotificationOpt.get();
        if (pushNotification.getSendResult() != null)
            return badRequestMessage("Зөвхөн хараахан илгээгээгүй push notification засварлах боломжтой");

        try {
            pushNotification.setType(request.getType());
            pushNotification.setSendType(request.getSendType());
            pushNotification.setScheduledDate(request.getScheduledDate());
            if (pushNotification.getScheduledDate() == null)
                pushNotification.setScheduledDate(LocalDateTime.now());
            pushNotification.setReceiverType(request.getReceiverType());
            pushNotification.setReceiver(request.getReceiver());
            if (request.getReceiverType() == PushNotificationReceiverType.USERNAME)
                pushNotification.setReceiver(request.getReceiver());
            pushNotification.setPriority(request.getPriority());
            pushNotification.setTitle(request.getTitle());
            pushNotification.setBody(request.getBody());
            pushNotification.setData(request.getData());
            pushNotification.setModifiedDate(LocalDateTime.now());
            pushNotification.setModifiedBy(user.getId());
            pushNotificationRepository.save(pushNotification);

            return ResponseEntity.ok(pushNotification.getId());
        } catch (MongoException | DataAccessException e) {
            log.error(e.getMessage(), e);
            return errorDatabase();
        }
    }
}
