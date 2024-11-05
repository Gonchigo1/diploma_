package mn.astvision.starter.api.systemconfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.model.systemconfig.SystemCron;
import mn.astvision.starter.model.systemconfig.enums.SystemCronType;
import mn.astvision.starter.repository.systemconfig.SystemCronRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Tergel
 */
@Slf4j
@Secured("ROLE_SYSTEM_CRON_MANAGE")
@RestController
@RequestMapping("/v1/system-cron")
@RequiredArgsConstructor
public class SystemCronApi extends BaseController {

    private final SystemCronRepository systemCronRepository;

    @GetMapping("all")
    public ResponseEntity<?> all(Boolean enable, @AuthUser User user) {
        try {
            if (enable == null)
                return badRequest();

            log.info("all cron enable: " + enable + ", by: " + user.getId());
            List<SystemCron> crons = systemCronRepository.findAllByCronTypeIn(Arrays.asList(SystemCronType.values()));
            if (crons != null) {
                for (SystemCron cron : crons)
                    cron.setEnabled(enable);
                systemCronRepository.saveAll(crons);
            }

            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return serverErrorMessage(e.getMessage());
        }
    }

    @GetMapping("payment-cron")
    public ResponseEntity<?> cron(Boolean enable, @AuthUser User user) {
        try {
            if (enable == null)
                return badRequest();

            log.info("payment cron enable: " + enable + ", by: " + user.getId());
            List<SystemCron> crons = systemCronRepository.findAllByCronTypeIn(SystemCronType.getPaymentCronTypes());
            if (crons != null) {
                for (SystemCron cron : crons)
                    cron.setEnabled(enable);
                systemCronRepository.saveAll(crons);
            }
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return serverErrorMessage(e.getMessage());
        }
    }

    @GetMapping("payment-cron-golomt")
    public ResponseEntity<?> golomt(Boolean enable, @AuthUser User user) {
        try {
            if (enable == null)
                return badRequest();

            log.info("payment cron golomt enable: " + enable + ", by: " + user.getId());
            List<SystemCron> crons = systemCronRepository
                    .findAllByCronTypeIn(SystemCronType.getPaymentGolomtCronTypes());
            if (crons != null) {
                for (SystemCron cron : crons)
                    cron.setEnabled(enable);
                systemCronRepository.saveAll(crons);
            }
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return serverErrorMessage(e.getMessage());
        }
    }

    @GetMapping("payment-cron-tdb")
    public ResponseEntity<?> tdb(Boolean enable, @AuthUser User user) {
        try {
            if (enable == null)
                return badRequest();

            log.info("payment cron tdb enable: " + enable + ", by: " + user.getId());
            List<SystemCron> crons = systemCronRepository.findAllByCronTypeIn(SystemCronType.getPaymentTdbCronTypes());
            if (crons != null) {
                for (SystemCron cron : crons)
                    cron.setEnabled(enable);
                systemCronRepository.saveAll(crons);
            }
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return serverErrorMessage(e.getMessage());
        }
    }

    @GetMapping("shared-cron")
    public ResponseEntity<?> shared(Boolean enable, @AuthUser User user) {
        try {
            if (enable == null)
                return badRequest();

            log.info("shared cron enable: " + enable + ", by: " + user.getId());
            List<SystemCron> crons = systemCronRepository.findAllByCronTypeIn(SystemCronType.getSharedCronTypes());
            if (crons != null) {
                for (SystemCron cron : crons)
                    cron.setEnabled(enable);
                systemCronRepository.saveAll(crons);
            }

            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return serverErrorMessage(e.getMessage());
        }
    }
}
