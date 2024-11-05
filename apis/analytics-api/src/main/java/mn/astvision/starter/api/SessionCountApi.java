package mn.astvision.starter.api;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.cron.AnalyticsCron;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author digz6666
 */
@RestController
@RequestMapping("/v1/session-count")
@RequiredArgsConstructor
public class SessionCountApi {

    private final AnalyticsCron analyticsCron;

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(analyticsCron.getSessionCountData());
    }
}
