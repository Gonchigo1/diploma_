package mn.astvision.starter.exceptionhandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dto.auth.response.AuthenticationFailureResponse;
import mn.astvision.starter.exception.auth.AuthorizationException;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * @author digz6666
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthorizationExceptionHandler {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final LocalizationUtil localizationUtil;

    @ExceptionHandler({AuthorizationException.class})
    protected ResponseEntity<AuthenticationFailureResponse> handleAuthorizationException(
            AuthorizationException ex, WebRequest request) {
        log.error("AuthorizationException : " + ex.getMessage());
        return globalExceptionHandler.buildResponse(
                ex,
                localizationUtil.buildMessage("error.UNAUTHORIZED"),
                HttpStatus.UNAUTHORIZED,
                request);
    }

    @ExceptionHandler({MalformedJwtException.class})
    protected ResponseEntity<AuthenticationFailureResponse> handleMalformedJwtException(
            MalformedJwtException ex, WebRequest request) {
        log.error("MalformedJwtException : " + ex.getMessage());
        return globalExceptionHandler.buildResponse(
                ex,
                localizationUtil.buildMessage("auth.jwt.malformed"),
                HttpStatus.UNAUTHORIZED,
                request);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    protected ResponseEntity<AuthenticationFailureResponse> handleExpiredJwtException(
            ExpiredJwtException ex, WebRequest request) {
        log.error("ExpiredJwtException : " + ex.getMessage());
        return globalExceptionHandler.buildResponse(
                ex,
                localizationUtil.buildMessage("auth.jwt.expired"),
                HttpStatus.UNAUTHORIZED,
                request);
    }
}
