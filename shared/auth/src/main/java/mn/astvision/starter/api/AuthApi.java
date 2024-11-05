package mn.astvision.starter.api;

import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.request.LoginRequest;
import mn.astvision.starter.api.response.AuthResponse;
import mn.astvision.starter.model.auth.BusinessRole;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.repository.auth.BusinessRoleRepository;
import mn.astvision.starter.repository.auth.UserRepository;
import mn.astvision.starter.service.TwoFAService;
import mn.astvision.starter.util.DateUtil;
import mn.astvision.starter.util.JwtTokenUtil;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;

/**
 * @author digz6666
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthApi {

    private final BusinessRoleRepository businessRoleRepository;
    private final JwtTokenUtil tokenUtil;
    private final LocalizationUtil localizationUtil;
    private final PasswordEncoder passwordEncoder;
    private final TwoFAService twoFAService;
    private final UserRepository userRepository;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.debug("Login request: " + loginRequest.getUsername());

        try {
            User user = userRepository.findByEmailAndDeletedFalse(loginRequest.getUsername());
            if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(localizationUtil.buildMessage("auth.usernameOrPasswordWrong"));

            if (!user.isActive())
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(localizationUtil.buildMessage("auth.accountDisabled"));

            if (!user.isEmailVerified())
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(localizationUtil.buildMessage("auth.emailNotVerified"));

            if (user.isUsing2fa()) {
                if (ObjectUtils.isEmpty(user.getSecretKey()))
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(localizationUtil.buildMessage("auth.two-fa.secret-key-not-found"));

                try {
                    boolean verified = twoFAService.verify(
                            loginRequest.getUsername().toLowerCase(),
                            user.getSecretKey(),
                            loginRequest.getCode());
                    if (!verified)
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(localizationUtil.buildMessage("auth.two-fa.error"));
                } catch (URISyntaxException e) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(localizationUtil.buildMessage("auth.two-fa.error"));
                }
            }

            Optional<BusinessRole> businessRoleOpt = businessRoleRepository.findById(user.getRole());
            if (businessRoleOpt.isEmpty())
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(localizationUtil.buildMessage("error.FORBIDDEN"));

            return ResponseEntity.ok(AuthResponse.builder()
                    .id(user.getId())
                    .name(user.getFullName())
                    .email(user.getEmail())
                    .image(null) // TODO
                    .token(tokenUtil.generateToken(user.getEmail(), false))
                    .expires(DateUtil.toLocalDate(tokenUtil.generateExpirationDate(false)))
                    .businessRole(businessRoleOpt.get())
                    .build());
        } catch (MongoException | DataAccessException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(localizationUtil.buildMessage("error.database"));
        }
    }

    @GetMapping("check-token")
    public ResponseEntity<?> checkToken(String token) {
        // log.debug("token: " + token);
        String username = tokenUtil.getUsernameFromToken(token);
        Date expirationDate = tokenUtil.getExpirationDateFromToken(token);
//        log.debug("expirationDate: " + expirationDate);
        if (!userRepository.existsByEmailAndDeletedFalse(username)
                || (expirationDate != null && expirationDate.before(new Date())))
            return ResponseEntity.badRequest().body(false);

        return ResponseEntity.ok(true);
    }

//    @GetMapping("refresh")
//    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
//        String token = request.getHeader(tokenHeader);
//        if (ObjectUtils.isEmpty(token)) {
//            return ResponseEntity.badRequest().body("Token empty");
//        }
//
//        String username = tokenUtil.getUsernameFromToken(token);
//        try {
//            User user = userRepository.findByEmailAndDeletedFalse(username);
//            if (user == null || !user.isActive()) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
//            }
//            if (!tokenUtil.canTokenBeRefreshed(token, user.getPasswordChangeDate())) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Password changed");
//            }
//
//            Optional<BusinessRole> businessRoleOpt = businessRoleRepository.findById(user.getBusinessRole());
//            if (businessRoleOpt.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Role not found");
//            }
//
//            AuthResponse authResponse = new AuthResponse();
//            authResponse.setToken(tokenUtil.refreshToken(token));
//            authResponse.setBusinessRole(businessRoleOpt.get());
//
//            return ResponseEntity.ok(authResponse);
//        } catch (MongoException | DataAccessException e) {
//            log.error(e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(localizationUtil.buildMessage("error.database"));
//        }
//    }

//    @PostMapping("reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest resetRequest) {
//        try {
//            User user = userRepository.findByEmailAndDeletedFalse(resetRequest.getUsername());
//            if (user == null || !user.isActive()) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(localizationUtil.buildMessage("error.FORBIDDEN"));
//            }
//
//            String passwordResetToken = RandomStringUtils.random(32, true, true);
//            user.setPasswordResetToken(passwordResetToken);
//            user.setPasswordResetTokenExpiryDate(LocalDateTime.now().plusMinutes(5L));
//            userRepository.save(user);
//
//            String url = String.format(passwordResetUrl, resetRequest.getUsername(), passwordResetToken);
//            boolean result = emailSenderService.send(
//                    "no-reply@astvision.mn",
//                    "no-reply@astvision.mn",
//                    resetRequest.getUsername(),
//                    "Нууц үг сэргээх",
//                    emailTemplateService.passwordReset(url),
//                    null);
//            if (!result) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("И-мэйл явуулахад алдаа гарлаа");
//            }
//
//            return ResponseEntity.ok("И-мэйл илгээлээ");
//        } catch (MongoException | DataAccessException e) {
//            log.error(e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(localizationUtil.buildMessage("error.database"));
//        }
//    }

//    @PostMapping("check-password-reset-token")
//    public ResponseEntity<?> checkPasswordResetToken(@RequestBody PasswordResetRequest resetRequest) {
//        try {
//            User user = userRepository.findByEmailAndDeletedFalse(resetRequest.getUsername());
//            if (user == null || !user.isActive()) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(localizationUtil.buildMessage("error.FORBIDDEN"));
//            }
//
//            if (!Objects.equals(user.getPasswordResetToken(), resetRequest.getResetToken())
//                    || user.getPasswordResetTokenExpiryDate() == null
//                    || user.getPasswordResetTokenExpiryDate().isAfter(LocalDateTime.now())) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token буруу байна");
//            }
//
//            return ResponseEntity.ok(true);
//        } catch (MongoException | DataAccessException e) {
//            log.error(e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(localizationUtil.buildMessage("error.database"));
//        }
//    }

//    @Secured("ROLE_DEFAULT")
//    @PostMapping("update-password")
//    public ResponseEntity<?> setPassword(@RequestBody LoginRequest updateRequest) {
//        try {
//            User user = userRepository.findByEmailAndDeletedFalse(updateRequest.getUsername());
//            if (user == null || !user.isActive()) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(localizationUtil.buildMessage("error.FORBIDDEN"));
//            }
//
//            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
//            user.setPasswordChangeDate(LocalDateTime.now());
//            userRepository.save(user);
//
//            return ResponseEntity.ok(true);
//        } catch (MongoException | DataAccessException e) {
//            log.error(e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(localizationUtil.buildMessage("error.database"));
//        }
//    }
}
