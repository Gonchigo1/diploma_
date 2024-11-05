package mn.astvision.starter.api.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.request.DeleteRequest;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.auth.BusinessRoleDao;
import mn.astvision.starter.model.auth.BusinessRole;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.model.auth.enums.ApplicationRole;
import mn.astvision.starter.repository.auth.BusinessRoleRepository;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Tergel
 */
@Slf4j
@Secured({"ROLE_BUSINESS_ROLE_VIEW", "ROLE_BUSINESS_ROLE_MANAGE", "ROLE_USER_VIEW", "ROLE_USER_MANAGE"})
@RestController
@RequestMapping("/v1/business-role")
@RequiredArgsConstructor
public class BusinessRoleApi {

    private final BusinessRoleRepository businessRoleRepository;
    private final BusinessRoleDao businessRoleDao;
    private final LocalizationUtil localizationUtil;

    @GetMapping
    public ResponseEntity<?> list(String role, ApplicationRole applicationRole, AntdPagination pagination) {
        AntdTableDataList<BusinessRole> listData = new AntdTableDataList<>();

        pagination.setTotal(businessRoleDao.count(role, applicationRole));
        listData.setPagination(pagination);
        listData.setList(businessRoleDao.list(role, applicationRole,
                PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize())));

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?> select(String role, ApplicationRole applicationRole) {
        return ResponseEntity.ok(businessRoleDao.list(role, applicationRole, null));
    }

    @Secured("ROLE_BUSINESS_ROLE_MANAGE")
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody BusinessRole createRequest, @AuthUser User user) {
        if (businessRoleRepository.existsById(createRequest.getRole()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(localizationUtil.buildMessage("data.exists"));

        log.info("Create business role: " + createRequest.getRole() + " by " + user.getEmail());
        BusinessRole businessRole = new BusinessRole();
        businessRole.setRole(createRequest.getRole());
        businessRole.setName(createRequest.getName());
        businessRole.setApplicationRoles(createRequest.getApplicationRoles());
        businessRoleRepository.save(businessRole);

        return ResponseEntity.ok(true);
    }

    @Secured("ROLE_BUSINESS_ROLE_MANAGE")
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody BusinessRole updateRequest) {
        Optional<BusinessRole> businessRoleOpt = businessRoleRepository.findById(updateRequest.getKey());
        if (businessRoleOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(localizationUtil.buildMessage("data.notFound"));

        log.debug("Update business role: " + updateRequest);
        BusinessRole businessRole = businessRoleOpt.get();
        businessRole.setName(updateRequest.getName());
        businessRole.setApplicationRoles(updateRequest.getApplicationRoles());
        businessRoleRepository.save(businessRole);

        return ResponseEntity.ok(true);
    }

    @Secured("ROLE_BUSINESS_ROLE_MANAGE")
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteMulti(@RequestBody DeleteRequest deleteRequest) {
        if (ObjectUtils.isEmpty(deleteRequest.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(localizationUtil.buildMessage("error.BAD_REQUEST"));

        BusinessRole businessRole = businessRoleRepository.findById(deleteRequest.getId()).orElse(null);
        if (businessRole == null)
            return ResponseEntity.badRequest().body("Цахим ажлын байр олдсонгүй: " + deleteRequest.getId());

        log.debug("Deleting business roles: " + deleteRequest);
        businessRoleRepository.deleteById(deleteRequest.getId());
        return ResponseEntity.ok(true);
    }
}
