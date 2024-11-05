package mn.astvision.starter.api.systemconfig;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.system.ModuleToggleRequest;
import mn.astvision.starter.model.systemconfig.SystemApiConf;
import mn.astvision.starter.repository.systemconfig.SystemApiConfRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tergel
 */
@Slf4j
@Secured("ROLE_SYSTEM_MODULE_MANAGE")
@RestController
@RequestMapping("/v1/system-api")
@RequiredArgsConstructor
public class SystemApiConfController extends BaseController {

    private final SystemApiConfRepository systemApiConfRepository;

    @PostMapping("toggle-module")
    public ResponseEntity<?> toggleModule(
        @Valid @RequestBody ModuleToggleRequest request) {
        try {
            final var moduleName = request.getModuleType();
            final var existingModule = systemApiConfRepository.findByModuleType(moduleName);

            SystemApiConf conf;
            if (existingModule.isPresent()) {
                conf = existingModule.get();
                conf.setEnabled(!conf.isEnabled());
            } else {
                conf = new SystemApiConf();
                conf.setEnabled(false);
                conf.setModuleType(moduleName);
            }

            return ResponseEntity.ok(systemApiConfRepository.save(conf));
        } catch (Exception e) {
            return serverErrorMessage(e.getMessage());
        }
    }
}
