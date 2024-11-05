package mn.astvision.starter.api.teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.BookTypeDao;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

}
