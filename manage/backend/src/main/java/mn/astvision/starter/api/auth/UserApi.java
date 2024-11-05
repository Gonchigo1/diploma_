package mn.astvision.starter.api.auth;

import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.CreateUserRequest;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.auth.UserDao;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.repository.auth.UserRepository;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author digz6666
 */
@Slf4j
@Secured({"ROLE_USER_VIEW", "ROLE_USER_MANAGE"})
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserApi extends BaseController {

    private final LocalizationUtil localizationUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> list(
            String role,
            String search,
            Boolean using2fa,
            Boolean emailVerified,
            Boolean phoneVerified,
            Boolean active,
            AntdPagination pagination) {
        AntdTableDataList<User> listData = new AntdTableDataList<>();

        pagination.setTotal(userDao.count(
                role,
                search,
                using2fa,
                emailVerified,
                phoneVerified,
                active
                ));
        listData.setPagination(pagination);
        listData.setList(userDao.list(
                role,
                search,
                using2fa,
                emailVerified,
                phoneVerified,
                active,
                pagination.toPageRequest()));
        return ResponseEntity.ok(listData);
    }

    @PostMapping("create")
    @Secured("ROLE_USER_MANAGE")
    public ResponseEntity<?> create(@RequestBody CreateUserRequest createRequest, @AuthUser User user) {
        try {
            if (ObjectUtils.isEmpty(createRequest.getEmail()) ||
                    ObjectUtils.isEmpty(createRequest.getPassword()) ||
                    ObjectUtils.isEmpty(createRequest.getRole())
            )
                return badRequest();
            if (userRepository.existsByEmailAndDeletedFalse(createRequest.getEmail().toLowerCase()))
                return ResponseEntity.badRequest().body("Имэйл хаяг бүртгэлтэй байна");

            log.debug("create ->" + createRequest + ", by->" + user.getId());

            User userToCreate = new User();
            userToCreate.setUsername(createRequest.getUsername());
            userToCreate.setEmail(createRequest.getEmail().toLowerCase());
            userToCreate.setPassword(passwordEncoder.encode(createRequest.getPassword()));
            userToCreate.setRole(createRequest.getRole());
            userToCreate.setMobile(createRequest.getMobile());
            userToCreate.setLastName(createRequest.getLastName());
            userToCreate.setFirstName(createRequest.getFirstName());
            userToCreate.setAddress(createRequest.getAddress());
            userToCreate.setOrgName(createRequest.getOrgName());
            userToCreate.setRegistryNumber(createRequest.getRegistryNumber());

            userToCreate.setEmailVerified(true);
            userToCreate.setActive(createRequest.isActive());
            userToCreate.setCreatedBy(user.getId());
            userToCreate.setCreatedDate(LocalDateTime.now());
            userToCreate = userRepository.save(userToCreate);

            return ResponseEntity.ok(userToCreate.getId());
        } catch (MongoException | DataAccessException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(localizationUtil.buildMessage("error.database"));
        }
    }

    @Secured("ROLE_USER_MANAGE")
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody CreateUserRequest updateRequest, @AuthUser User user) {
        try {
            User userToModify = userRepository.findByIdAndDeletedFalse(updateRequest.getId());
            if (userToModify == null)
                return ResponseEntity.badRequest().body("Хэрэглэгчийн мэдээлэл олдсонгүй");

            if (userRepository.existsByEmailAndIdNotAndDeletedFalse(
                    updateRequest.getEmail().toLowerCase(),
                    updateRequest.getId()))
                return ResponseEntity.badRequest().body("Имэйл хаяг бүртгэлтэй байна");

            log.debug("update ->" + updateRequest + ", id: " + updateRequest.getId() + ", by " + user.getId());

            if (!ObjectUtils.isEmpty(updateRequest.getPassword()))
                userToModify.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

            userToModify.setMobile(updateRequest.getMobile());
            userToModify.setRole(updateRequest.getRole());
            userToModify.setLastName(updateRequest.getLastName());
            userToModify.setFirstName(updateRequest.getFirstName());
            userToModify.setOrgName(updateRequest.getOrgName());
            userToModify.setActive(updateRequest.isActive());

            userToModify.setModifiedDate(LocalDateTime.now());
            userToModify.setModifiedBy(user.getId());

            userRepository.save(userToModify);
            return ResponseEntity.ok(userToModify.getId());
        } catch (MongoException | DataAccessException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(localizationUtil.buildMessage("error.database"));
        }
    }

    @Secured("ROLE_USER_MANAGE")
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam String id, @AuthUser User user) {
        try {
            User userToModify = userRepository.findByIdAndDeletedFalse(id);
            if (userToModify == null)
                return ResponseEntity.badRequest()
                        .body(localizationUtil.buildMessage("data.notFound"));

            log.debug("delete: id->" + id + ", by->" + user.getId());

            userToModify.setDeleted(true);
            userToModify.setActive(false);
//            user.setOldUsername(user.getUsername());
//            user.setUsername(user.getUsername() + "_" + user.getId());
//            user.setOldEmail(user.getEmail());
//            user.setEmail(null);
            userToModify.setModifiedDate(LocalDateTime.now());
            userToModify.setModifiedBy(user.getId());

            userRepository.save(userToModify);
            return ResponseEntity.ok(userToModify.getId());
        } catch (MongoException | DataAccessException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(localizationUtil.buildMessage("error.database"));
        }
    }

    @Secured("ROLE_USER_MANAGE")
    @PostMapping("disable-auth")
    public ResponseEntity<?> disableAuth(@RequestBody CreateUserRequest disable2FaRequest, @AuthUser User user) {
        User userToModify = userRepository.findByIdAndDeletedFalse(disable2FaRequest.getId());
        if (userToModify == null)
            return ResponseEntity.badRequest()
                    .body(localizationUtil.buildMessage("data.notFound"));

        if (!userToModify.isUsing2fa())
            return ResponseEntity.badRequest().body("Хэрэглэгчийн 2FA идэвхгүй байна");

        log.debug("disable 2FA: userId->" + disable2FaRequest.getId() + ", by->" + user.getId());

        userToModify.setUsing2fa(false);
        userToModify.setSecretKey(null);
        userToModify.setModifiedDate(LocalDateTime.now());
        userToModify.setModifiedBy(user.getId());

        userRepository.save(userToModify);
        return ResponseEntity.ok(userToModify.getId());
    }
}
