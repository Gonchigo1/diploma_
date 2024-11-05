package mn.astvision.starter.service;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.dao.TeacherDao;
import mn.astvision.starter.model.Teacher;
import mn.astvision.starter.repository.TeacherRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Component
public class TeacherService {
    private final TeacherDao teacherDao;
    private final TeacherRepository teacherRepository;
    public void deleteById(String id) {
        teacherRepository.deleteById(id);
    }
    public Iterable<Teacher> list(String school,
                                  String teacherLName,
                                  String name,
                                  String phone,
                                  String email,
                                  String userName,
                                  String Password,
                                  Boolean active,
                                  Pageable pageable) {
        Iterable<Teacher> listData = teacherDao.list(
                school,
                teacherLName,
                name,
                phone,
                email,
                userName,
                Password,
                active,
                pageable);

        for (Teacher locale : listData) {
            fillRelatedData(locale);
        }

        return listData;
    }

    private void fillRelatedData(Teacher teacher) {
        if (!ObjectUtils.isEmpty(teacher.getSchool())) { // Use getSchool() instead of getSchoolName()
            teacherRepository.findByIdAndDeletedFalse(teacher.getSchool());
        }
    }


}