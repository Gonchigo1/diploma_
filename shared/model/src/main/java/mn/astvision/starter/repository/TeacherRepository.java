package mn.astvision.starter.repository;

import mn.astvision.starter.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {
    boolean existsByTeacherEmailAndDeletedFalse(String teacherEmail); // Corrected reference

    boolean existsByIdAndDeletedFalse(String id);

    Optional<Teacher> findByIdAndDeletedFalse(String id);

    void deleteById(String id);
    boolean existsByTeacherFirstnameAndDeletedFalse(String teacherFirstname); // Adjusted name

    List<Teacher> findAllByTeacherFirstnameGreaterThan(String teacherFirstname); // Assuming teacherFirstname is a String
}
