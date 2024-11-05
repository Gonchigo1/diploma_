package mn.astvision.starter.api.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author digz6666
 */
@Slf4j
@Secured({"ROLE_DEFAULT"})
@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountApi {
}
