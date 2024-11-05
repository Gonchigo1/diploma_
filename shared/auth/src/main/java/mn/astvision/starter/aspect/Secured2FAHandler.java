package mn.astvision.starter.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.exception.auth.AuthorizationException;
import mn.astvision.starter.exception.auth.Secured2FAException;
import mn.astvision.starter.repository.auth.UserRepository;
import mn.astvision.starter.service.TwoFAService;
import mn.astvision.starter.util.LocalizationUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class Secured2FAHandler {

    private final LocalizationUtil localizationUtil;
    private final ObjectMapper objectMapper;
    private final TwoFAService twoFAService;
    private final UserRepository userRepository;

    @Around(" @annotation(mn.astvision.starter.annotations.Secured2FA) && args(.., @RequestBody body)")
    public Object validateAspect(ProceedingJoinPoint pjp, final Object body)
            throws Secured2FAException {

        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        try {
            if (request.getMethod().equals("GET"))
                return pjp.proceed();

            if (body == null)
                throw new Secured2FAException(
                        localizationUtil.buildMessage("auth.two-fa.code-required"));

            // POST, PUT, DELETE
            final var loggedPrincipal = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            if (loggedPrincipal instanceof String)
                throw new Secured2FAException(
                        localizationUtil.buildMessage("auth.bearer-token.required"));

            final var springUser = ((org.springframework.security.core.userdetails.User) loggedPrincipal);
            final String json = objectMapper.writeValueAsString(body);
            final ObjectNode node = objectMapper.readValue(json, ObjectNode.class);
            final var authCode = node.get("authCode");
            if (authCode.isNull())
                throw new Secured2FAException(
                        localizationUtil.buildMessage("auth.two-fa.code-required"));

            final var user = userRepository.findByEmail(springUser.getUsername().toLowerCase());
            if (user == null)
                throw new AuthorizationException(
                        localizationUtil.buildMessage("error.FORBIDDEN"));

            if (!user.isUsing2fa())
                throw new AuthorizationException(
                        localizationUtil.buildMessage("auth.two-fa.not-enabled"));

            if (!twoFAService.verify(user, authCode.textValue()))
                throw new Secured2FAException(
                        localizationUtil.buildMessage("auth.two-fa.code-invalid"));

            return pjp.proceed();
        } catch (Throwable t) {
            log.error(localizationUtil.buildMessage("auth.two-fa.error"));
            throw new Secured2FAException(t.getMessage());
        }
    }
}
