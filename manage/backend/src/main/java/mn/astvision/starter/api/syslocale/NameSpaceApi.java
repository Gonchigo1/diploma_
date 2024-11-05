package mn.astvision.starter.api.syslocale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.syslocale.NameSpaceDao;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.model.syslocale.NameSpace;
import mn.astvision.starter.repository.syslocale.NameSpaceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Maroon
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Secured({"ROLE_LOCALE_VIEW", "ROLE_LOCALE_MANAGE"})
@RequestMapping("/v1/name-space")
public class NameSpaceApi {

    private final NameSpaceRepository nameSpaceRepository;
    private final NameSpaceDao nameSpaceDao;

    @GetMapping
    public ResponseEntity<?> list(String name, String value, Boolean active, AntdPagination pagination) {
        AntdTableDataList<NameSpace> listData = new AntdTableDataList<>();

        pagination.setTotal(nameSpaceDao.count(name, value, active));
        listData.setPagination(pagination);
        listData.setList(nameSpaceDao.list(name, value, active, pagination.toPageRequest()));

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?> select(String name, String value, Boolean active) {
        Iterable<NameSpace> listData = nameSpaceDao.list(name, value, active, null);
        return ResponseEntity.ok(listData);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok().body(nameSpaceRepository.findById(id).orElse(null));
    }

    @GetMapping("get-with-value/{value}")
    public ResponseEntity<?> getWithNameSpace(@PathVariable String value) {
        return ResponseEntity.ok().body(nameSpaceRepository.findByValue(value).orElse(null));
    }

    @Secured("ROLE_LOCALE_MANAGE")
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody List<NameSpace> createRequests, @AuthUser User user) {
        for (NameSpace item : createRequests) {
            if (ObjectUtils.isEmpty(item.getValue()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(item.getName() + " утга Хоосон байна.");
            if (nameSpaceRepository.existsByValueAndDeletedFalse(item.getValue().toLowerCase()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(item.getName() + " утга давхцаж байна.");

            log.debug("creating nameSpace -> " + item);
            item.setCreatedBy(user.getId());
            item.setCreatedDate(LocalDateTime.now());
        }

        nameSpaceRepository.saveAll(createRequests);
        return ResponseEntity.ok(true);
    }

    @Secured("ROLE_LOCALE_MANAGE")
    @PostMapping("delete")
    public ResponseEntity<?> deleteMulti(@RequestParam String id, @AuthUser User user) {
        Optional<NameSpace> typeOptional = nameSpaceRepository.findById(id);
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("NameSpace not found.");

        log.debug("delete id ->  " + id);
        NameSpace nameSpace = typeOptional.get();
        nameSpace.setDeleted(true);
        nameSpace.setActive(false);
        nameSpace.setModifiedDate(LocalDateTime.now());
        nameSpace.setModifiedBy(user.getId());

        nameSpaceRepository.save(nameSpace);
        return ResponseEntity.ok().body(true);
    }

    @Secured("ROLE_LOCALE_MANAGE")
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody NameSpace updateRequest, @AuthUser User user) {
        if (ObjectUtils.isEmpty(updateRequest.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID хоосон байна.");

        Optional<NameSpace> typeOptional = nameSpaceRepository.findById(updateRequest.getId());
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("NameSpace not found.");

        log.debug("updating nameSpace -> " + updateRequest);
        NameSpace nameSpace = typeOptional.get();
        nameSpace.setName(updateRequest.getName());
        nameSpace.setValue(updateRequest.getValue().toLowerCase());
        nameSpace.setActive(updateRequest.isActive());
        nameSpace.setModifiedBy(user.getId());
        nameSpace.setModifiedDate(LocalDateTime.now());
        nameSpaceRepository.save(nameSpace);

        return ResponseEntity.ok(nameSpace.getId());
    }
}
