package mn.astvision.starter.api.mobile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.model.systemconfig.SystemKey;
import mn.astvision.starter.model.systemconfig.SystemKeyValue;
import mn.astvision.starter.repository.systemconfig.SystemKeyValueRepository;
import mn.astvision.starter.service.systemconfig.SystemKeyValueService;
import mn.astvision.starter.api.request.AppVersionUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author Tergel
 */
@Slf4j
@Secured({"ROLE_CUSTOMER"})
@RestController
@RequestMapping("/v1/app-version")
@RequiredArgsConstructor
public class AppVersionApi extends BaseController {

    private final SystemKeyValueRepository systemKeyValueRepository;
    private final SystemKeyValueService systemKeyValueService;

    @PostMapping("ios")
    public ResponseEntity<?> ios(@Valid @RequestBody AppVersionUpdateRequest updateRequest) {
        SystemKeyValue keyValue = systemKeyValueService.get(SystemKey.APP_VERSION_IOS);
        if (ObjectUtils.isEmpty(keyValue))
            return badRequestMessage("App version мэдээлэл олдсонгүй");

        keyValue.setValue(updateRequest.getVersion());
        systemKeyValueRepository.save(keyValue);
        return ResponseEntity.ok(keyValue);
    }

    @PostMapping("android")
    public ResponseEntity<?> android(@Valid @RequestBody AppVersionUpdateRequest updateRequest) {
        SystemKeyValue keyValue = systemKeyValueService.get(SystemKey.APP_VERSION_ANDROID);
        if (ObjectUtils.isEmpty(keyValue))
            return badRequestMessage("App version мэдээлэл олдсонгүй");

        keyValue.setValue(updateRequest.getVersion());
        systemKeyValueRepository.save(keyValue);
        return ResponseEntity.ok(keyValue);
    }
}
