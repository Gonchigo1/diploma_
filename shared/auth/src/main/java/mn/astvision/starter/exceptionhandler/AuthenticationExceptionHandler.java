package mn.astvision.starter.exceptionhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dto.auth.response.AuthenticationFailureResponse;
import mn.astvision.starter.exception.TwoFAException;
import mn.astvision.starter.exception.auth.BusinessRoleException;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * @author digz6666
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthenticationExceptionHandler {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final LocalizationUtil localizationUtil;

    @ExceptionHandler({TwoFAException.class})
    protected ResponseEntity<AuthenticationFailureResponse> handleTwoFAException(
            TwoFAException ex, WebRequest request) {
        log.error("TwoFAException : " + ex.getMessage());
        return globalExceptionHandler.buildResponse(
                ex,
                localizationUtil.buildMessage("auth.two-fa.error"),
                HttpStatus.FORBIDDEN,
                request);
    }

    @ExceptionHandler({BadCredentialsException.class})
    protected ResponseEntity<AuthenticationFailureResponse> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {
        log.error("BadCredentialsException : " + ex.getMessage());
        return globalExceptionHandler.buildResponse(
                ex,
                localizationUtil.buildMessage("auth.usernameOrPasswordWrong"),
                HttpStatus.FORBIDDEN,
                request);
    }

    @ExceptionHandler({DisabledException.class})
    protected ResponseEntity<AuthenticationFailureResponse> handleDisabledException(
            DisabledException ex, WebRequest request) {
        log.error("DisabledException : " + ex.getMessage());
        return globalExceptionHandler.buildResponse(
                ex,
                localizationUtil.buildMessage("auth.accountDisabled"),
                HttpStatus.FORBIDDEN,
                request);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    protected ResponseEntity<AuthenticationFailureResponse> handleUsernameNotFoundException(
            UsernameNotFoundException ex, WebRequest request) {
        log.error("UsernameNotFoundException : " + ex.getMessage());
        return globalExceptionHandler.buildResponse(
                ex,
                localizationUtil.buildMessage("auth.usernameOrPasswordWrong"),
                HttpStatus.FORBIDDEN,
                request);
    }

    @ExceptionHandler({BusinessRoleException.class})
    protected ResponseEntity<AuthenticationFailureResponse> handleBusinessRoleException(
            BusinessRoleException ex, WebRequest request) {
        log.error("BusinessRoleException : " + ex.getMessage());
        return globalExceptionHandler.buildResponse(ex, null, HttpStatus.FORBIDDEN, request);
    }
}
