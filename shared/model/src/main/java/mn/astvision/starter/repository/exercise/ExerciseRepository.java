package mn.astvision.starter.repository.exercise;


import mn.astvision.starter.model.exercise.Exercise;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends MongoRepository<Exercise,String> {
    Optional<Exercise> findByIdAndDeletedFalse(String id);

    int countByBookIdAndDeletedFalse(String bookId);
    int countByLessonIdAndDeletedFalse(String lessonId);

}
