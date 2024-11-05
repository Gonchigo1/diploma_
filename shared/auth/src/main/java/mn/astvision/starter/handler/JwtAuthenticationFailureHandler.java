package mn.astvision.starter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dto.auth.response.AuthenticationFailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

/**
 * @author digz6666
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            objectMapper.writeValue(response.getOutputStream(), AuthenticationFailureResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .timestamp(Instant.now().toEpochMilli())
                    .error(e.getClass().getSimpleName())
                    .message("Authentication error : " + e.getMessage())
                    .path(request.getRequestURI())
                    .build());
        } catch (IOException ex) {
            log.error("Authentication error : " + e.getMessage(), ex.getMessage());
        }
    }
}
