package mn.astvision.starter.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.request.IdRequest;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.notification.NotificationDao;
import mn.astvision.starter.exception.notification.NotificationException;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.model.notification.Notification;
import mn.astvision.starter.model.notification.enums.NotificationRelatedDataType;
import mn.astvision.starter.model.notification.enums.NotificationStatus;
import mn.astvision.starter.service.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author digz6666
 */
@Slf4j
@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
public class NotificationApi extends BaseController {

    private final NotificationDao notificationDao;
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> list(
            NotificationRelatedDataType dataType,
            NotificationStatus status,
            AntdPagination pagination,
            @AuthUser User user) {

        AntdTableDataList<Notification> dataList = new AntdTableDataList<>();

        try {
            pagination.setTotal(notificationDao.count(user.getId(), dataType, status));
            dataList.setPagination(pagination);
            List<Notification> notificationList = notificationDao.list(
                    user.getId(),
                    dataType,
                    status,
                    pagination.toPageRequest());
            dataList.setList(notificationList);

            // dataList.setList(notificationList.stream().map(notif -> {
            // return notificationService.fillData(notif);
            // }).collect(Collectors.toList()));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return ResponseEntity.ok(dataList);
    }

    @PostMapping("read")
    public ResponseEntity<?> read(@Valid @RequestBody IdRequest request, @AuthUser User user) {
        try {
            return ResponseEntity.ok(notificationService.read(request.getId(), user.getId()));
        } catch (NotificationException e) {
            return badRequestMessage(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverError();
        }
    }

    @PostMapping("clean")
    public ResponseEntity<?> clean(@AuthUser User user) {
        try {
            notificationService.clean(user.getId());

            return ResponseEntity.ok(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverError();
        }
    }
}
