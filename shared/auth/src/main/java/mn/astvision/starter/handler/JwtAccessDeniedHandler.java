package mn.astvision.starter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dto.auth.response.AuthenticationFailureResponse;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

/**
 * @author digz6666
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    private final LocalizationUtil localizationUtil;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException e) {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            objectMapper.writeValue(response.getOutputStream(), AuthenticationFailureResponse.builder()
                    .status(HttpStatus.FORBIDDEN.value())
                    .timestamp(Instant.now().toEpochMilli())
                    .error(e.getClass().getSimpleName())
                    .message(localizationUtil.buildMessage("error.FORBIDDEN"))
                    .path(request.getRequestURI())
                    .build());
        } catch (IOException ex) {
            log.error("Access denied : " + e.getMessage(), ex.getMessage());
        }
    }
}
