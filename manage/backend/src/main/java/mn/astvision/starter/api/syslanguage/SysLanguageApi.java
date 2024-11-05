package mn.astvision.starter.api.syslanguage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.syslanguage.SysLanguageDao;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.model.syslanguage.SysLanguage;
import mn.astvision.starter.repository.syslanguage.SysLanguageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Maroon
 */
@Slf4j
@RestController
@Secured({"ROLE_LOCALE_VIEW", "ROLE_LOCALE_MANAGE"})
@RequestMapping("/v1/sys-language")
@RequiredArgsConstructor
public class SysLanguageApi {

    private final SysLanguageRepository sysLanguageRepository;
    private final SysLanguageDao sysLanguageDao;

    @GetMapping
    public ResponseEntity<?> list(String name, String code, Boolean active, AntdPagination pagination) {
//        log.debug("Reference type list -> name: " + name + ", deleted: " + deleted + ", pagination: " + pagination);
        AntdTableDataList<SysLanguage> listData = new AntdTableDataList<>();

        pagination.setTotal(sysLanguageDao.count(name, code, active));
        listData.setPagination(pagination);
        listData.setList(sysLanguageDao.list(name, code, active,
                PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(),
                        Sort.by(Sort.Direction.fromString("DESC"), "order")
                )));

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?> select(String name, String code, Boolean active) {
        Iterable<SysLanguage> listData = sysLanguageDao.list(name, code, active, null);
        return ResponseEntity.ok(listData);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok().body(sysLanguageRepository.findById(id).orElse(null));
    }

    @GetMapping("get-with-code/{code}")
    public ResponseEntity<?> getWithCode(@PathVariable String code) {
        return ResponseEntity.ok().body(sysLanguageRepository.findByCode(code).orElse(null));
    }

    @Secured("ROLE_LOCALE_MANAGE")
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody SysLanguage createRequest, @AuthUser User user) {
        if (ObjectUtils.isEmpty(createRequest.getCode()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("code Хоосон байна.");

        if (sysLanguageRepository.existsByCodeAndDeletedFalse(createRequest.getCode().toLowerCase()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("code давхцаж байна.");

        log.debug("create -> " + createRequest);
        SysLanguage sysLanguage = new SysLanguage();
        sysLanguage.setName(createRequest.getName());
        sysLanguage.setCode(createRequest.getCode().toLowerCase());
        sysLanguage.setOrder(createRequest.getOrder());
        sysLanguage.setActive(createRequest.isActive());
        sysLanguage.setCreatedBy(user.getId());
        sysLanguage.setCreatedDate(LocalDateTime.now());
        sysLanguage = sysLanguageRepository.save(sysLanguage);

        return ResponseEntity.ok(sysLanguage.getId());
    }

    @Secured("ROLE_LOCALE_MANAGE")
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody SysLanguage updateRequest, @AuthUser User user) {
        if (ObjectUtils.isEmpty(updateRequest.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID хоосон байна.");

        Optional<SysLanguage> typeOptional = sysLanguageRepository.findById(updateRequest.getId());
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SysLanguage not found.");

        log.debug("update -> " + updateRequest);
        SysLanguage sysLanguage = typeOptional.get();
        sysLanguage.setName(updateRequest.getName());
        sysLanguage.setCode(updateRequest.getCode().toLowerCase());
        sysLanguage.setOrder(updateRequest.getOrder());
        sysLanguage.setActive(updateRequest.isActive());
        sysLanguage.setModifiedBy(user.getId());
        sysLanguage.setModifiedDate(LocalDateTime.now());
        sysLanguage = sysLanguageRepository.save(sysLanguage);

        return ResponseEntity.ok(sysLanguage.getId());
    }

    @Secured("ROLE_LOCALE_MANAGE")
    @PostMapping("delete")
    public ResponseEntity<?> deleteMulti(@RequestParam String id, @AuthUser User user) {
        Optional<SysLanguage> typeOptional = sysLanguageRepository.findById(id);
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SysLanguage not found.");

        log.debug("delete id ->  " + id);
        SysLanguage sysLanguage = typeOptional.get();
        sysLanguage.setDeleted(true);
        sysLanguage.setActive(false);
        sysLanguage.setModifiedBy(user.getId());
        sysLanguage.setModifiedDate(LocalDateTime.now());
        sysLanguageRepository.save(sysLanguage);

        return ResponseEntity.ok().body(true);
    }
}
