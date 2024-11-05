package mn.astvision.starter.api.syslocale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.syslocale.SysLocaleDao;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.model.syslanguage.SysLanguage;
import mn.astvision.starter.model.syslocale.NameSpace;
import mn.astvision.starter.model.syslocale.SysLocale;
import mn.astvision.starter.repository.syslanguage.SysLanguageRepository;
import mn.astvision.starter.repository.syslocale.NameSpaceRepository;
import mn.astvision.starter.repository.syslocale.SysLocaleRepository;
import mn.astvision.starter.service.syslocale.SysLocaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Maroon
 */
@Slf4j
@Secured({"ROLE_LOCALE_VIEW", "ROLE_LOCALE_MANAGE"})
@RestController
@RequestMapping("/v1/locale")
@RequiredArgsConstructor
public class SysLocaleApi {

    private final SysLocaleRepository sysLocaleRepository;
    private final SysLocaleDao sysLocaleDao;
    private final SysLocaleService sysLocaleService;
    private final NameSpaceRepository nameSpaceRepository;
    private final SysLanguageRepository sysLanguageRepository;

    @GetMapping
    public ResponseEntity<?> list(
            String nsId,
            String lng,
            String field,
            String translation,
            Boolean active,
            AntdPagination pagination) {
        AntdTableDataList<SysLocale> listData = new AntdTableDataList<>();

        pagination.setTotal(sysLocaleDao.count(nsId, lng, field, translation, active));
        listData.setPagination(pagination);
        listData.setList(sysLocaleService.list(nsId, lng, field, translation, active, pagination.toPageRequest()));

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?> select(String nsId, String lng, String field, String translation, Boolean active) {
        Iterable<SysLocale> listData = sysLocaleDao.list(nsId, lng, field, translation, active, null);
        return ResponseEntity.ok(listData);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok().body(sysLocaleRepository.findById(id).orElse(null));
    }

    @GetMapping("get-with-code/{code}")
    public ResponseEntity<?> getWithCode(@PathVariable String code) {
        return ResponseEntity.ok().body(sysLocaleRepository.findByCode(code).orElse(null));
    }

    @GetMapping("get-locale/{lng}/{ns}")
    public ResponseEntity<?> getLocale(@PathVariable String lng, @PathVariable String ns) {
        NameSpace nameSpace = nameSpaceRepository.findByValue(ns).orElse(null);
        if (ObjectUtils.isEmpty(nameSpace))
            return ResponseEntity.badRequest().body("Name space олдсонгүй.");

        SysLanguage sysLanguage = sysLanguageRepository.findByCode(lng).orElse(null);
        if (ObjectUtils.isEmpty(sysLanguage))
            return ResponseEntity.badRequest().body("Системийн хэл олдсонгүй.");

        Map<String, String> listData = new HashMap<>();
        Iterable<SysLocale> locales = sysLocaleDao.list(
                ns,
                lng,
                null,
                null,
                true,
                null);

        for (SysLocale locale : locales){
            listData.put(locale.getField(), locale.getTranslation());
        }

        return ResponseEntity.ok().body(listData);
    }

    @Secured("ROLE_LOCALE_MANAGE")
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody List<SysLocale> createRequests, @AuthUser User user) {
        for (SysLocale item : createRequests) {
            if (ObjectUtils.isEmpty(item.getTranslation()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(item.getField() + item.getLng() + " утга Хоосон байна.");

            if (sysLocaleRepository.existsByFieldAndLng(item.getField(), item.getLng()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(item.getField() + item.getLng() + " орчуулга бүртгэсэн байна.");

            log.debug("creating locale -> " + item);
            item.setCreatedBy(user.getId());
            item.setCreatedDate(LocalDateTime.now());
        }

        sysLocaleRepository.saveAll(createRequests);

        return ResponseEntity.ok(true);
    }

    @Secured("ROLE_LOCALE_MANAGE")
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody SysLocale updateRequest, @AuthUser User user) {
        if (ObjectUtils.isEmpty(updateRequest.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID хоосон байна.");

        Optional<SysLocale> typeOptional = sysLocaleRepository.findById(updateRequest.getId());
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Locale not found.");

        log.debug("updating locale -> " + updateRequest);
        SysLocale locale = typeOptional.get();
        locale.setField(updateRequest.getField());
        locale.setLng(updateRequest.getLng());
        locale.setCode(updateRequest.getCode());
        locale.setTranslation(updateRequest.getTranslation());
        locale.setActive(updateRequest.isActive());
        locale.setModifiedBy(user.getId());
        locale.setModifiedDate(LocalDateTime.now());

        locale = sysLocaleRepository.save(locale);

        return ResponseEntity.ok(locale.getId());
    }

    @Secured("ROLE_LOCALE_MANAGE")
    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestParam String id, @AuthUser User user) {
        Optional<SysLocale> typeOptional = sysLocaleRepository.findById(id);
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Locale not found.");

        log.debug("delete id ->  " + id);
        SysLocale locale = typeOptional.get();
        locale.setDeleted(true);
        locale.setActive(false);
        locale.setModifiedDate(LocalDateTime.now());
        locale.setModifiedBy(user.getId());

        sysLocaleRepository.save(locale);
        return ResponseEntity.ok().body(true);
    }
}
