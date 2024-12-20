package mn.astvision.starter.api.mobile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.mobile.DeviceTokenDao;
import mn.astvision.starter.model.mobile.DeviceToken;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author digz6666
 */
@Slf4j
@Secured("ROLE_DEVICE_TOKEN_VIEW")
@RestController
@RequestMapping("/v1/device-token")
@RequiredArgsConstructor
public class DeviceTokenApi {

    private final DeviceTokenDao deviceTokenDao;

    @GetMapping
    public ResponseEntity<?> list(
            String os,
            String token,
            String deviceId,
            String email,
            String ip,
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

        try {
            AntdTableDataList<DeviceToken> listData = new AntdTableDataList<>();
            pagination.setTotal(deviceTokenDao.count(os, token, deviceId, email, ip));
            listData.setPagination(pagination);
            listData.setList(deviceTokenDao.list(os, token, deviceId, email, ip, pagination.toPageRequest()));

            return ResponseEntity.ok(listData);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("check-all")
    public ResponseEntity<?> checkAll() {
        // TODO
        return null;
    }

//    @Secured("ROLE_PUSH_NOTIFICATION_MANAGE")
//    @RequestMapping(value = "import", method = RequestMethod.POST, consumes = {"multipart/form-data"})
//    public ResponseEntity<?> importExcel(
//            @RequestPart("file") MultipartFile file) {
//
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body(new BaseResponse("Файл хоосон байна"));
//        }
//
//        try {
//            int importedRecords = deviceTokenImportUtil.importExcel(file.getInputStream());
//            return ResponseEntity.ok(new BaseResponse(true, importedRecords + " бичлэг import хийгдлээ", null));
//        } catch (IOException | MongoException e) {
//            log.error(e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(e.getMessage()));
//        }
//    }
}
