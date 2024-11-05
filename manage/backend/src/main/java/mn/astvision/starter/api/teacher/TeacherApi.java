package mn.astvision.starter.api.teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.TeacherDao;
import mn.astvision.starter.model.Teacher;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.repository.TeacherRepository;
import mn.astvision.starter.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
//@Secured({"ROLE_ARTICLE_VIEW", "ROLE_ARTICLE_MANAGE"})
@RestController
@RequestMapping("/v1/teacher")
@RequiredArgsConstructor
public class TeacherApi extends BaseController {
    private final TeacherRepository teacherRepository;
    private final TeacherDao teacherDao;

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<?> list(
            String school,
            String teacherLName,
            String name,
            String phone,
            String email,
            String userName,
            String Password,
            Boolean active,
            AntdPagination pagination
    ) {
        AntdTableDataList<Teacher> listData = new AntdTableDataList<>();

        pagination.setTotal(
                teacherDao.count(
                        school,
                        teacherLName,
                        name,
                        phone,
                        email,
                        userName,
                        Password,
                        active
                )
        );
        listData.setPagination(pagination);
        listData.setList(
                teacherService.list(
                        school,
                        teacherLName,
                        name,
                        phone,
                        email,
                        userName,
                        Password,
                        active,
                        pagination.toPageRequest()
                )
        );

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?> select(
            String school,
            String teacherLName,
            String name,
            String phone,
            String email,
            String userName,
            String Password,
            Boolean active
    ) {
        Iterable<Teacher> listData = teacherDao.list(
                school,
                teacherLName,
                name,
                phone,
                email,
                userName,
                Password,
                active,
                null
        );
        return ResponseEntity.ok(listData);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        // Use the injected instance of teacherRepository
        return ResponseEntity.ok().body(teacherRepository.findByIdAndDeletedFalse(id).orElse(null));
    }


    @PostMapping("create")
    public ResponseEntity<?> create(
            @RequestBody Teacher request,
            @AuthUser User user
    ) {
        if (ObjectUtils.isEmpty(request.getTeacherLoginName()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Login name or password is empty.");

        try {
            request.setCreatedBy(user.getId());
            request.setCreatedDate(LocalDateTime.now());

            return ResponseEntity.ok(teacherRepository.save(request));
        } catch (Exception e) {
            log.error(e.getMessage());
            return badRequestMessage(e.getMessage());
        }
    }

    @PostMapping("update")
    public ResponseEntity<?> update(
            @RequestBody Teacher updateRequest,
            @AuthUser User user
    ) {
        if (ObjectUtils.isEmpty(updateRequest.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID is empty.");

        Optional<Teacher> typeOptional = teacherRepository.findByIdAndDeletedFalse(updateRequest.getId()); // Use injected teacherRepository
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Teacher not found.");

        log.debug("updating teacher -> " + updateRequest);
        Teacher type = typeOptional.get();

        type.setSchool(updateRequest.getSchool());
        type.setTeacherLastName(updateRequest.getTeacherLastName());
        type.setTeacherFirstname(updateRequest.getTeacherFirstname());
        type.setTeacherPhone(updateRequest.getTeacherPhone());
        type.setTeacherEmail(updateRequest.getTeacherEmail());
        type.setTeacherLoginName(updateRequest.getTeacherLoginName());
        type.setPassword(updateRequest.getPassword());
        type.setModifiedBy(user.getId());
        type.setModifiedDate(LocalDateTime.now());

        type = teacherRepository.save(type);

        return ResponseEntity.ok(type);
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteTeacher(@RequestParam String id) {
        Optional<Teacher> typeOptional = teacherRepository.findById(id);
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Teacher not found.");
        return ResponseEntity.ok().body(true);
    }

//    @PostMapping("delete")
//    public ResponseEntity<?> delete(
//            @RequestParam String id,
//            @AuthUser User user
//    ) {
//        Optional<SysLocale> typeOptional = sysLocaleRepository.findById(id);
//        if (typeOptional.isEmpty())
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Locale not found.");
//
//        log.debug("delete id ->  " + id);
//        SysLocale locale = typeOptional.get();
//        locale.setDeleted(true);
//        locale.setActive(false);
//        locale.setModifiedDate(LocalDateTime.now());
//        locale.setModifiedBy(user.getId());
//
//        sysLocaleRepository.save(locale);
//        return ResponseEntity.ok().body(true);
//    }
}
