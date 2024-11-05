package mn.astvision.starter.repository.lesson;

import mn.astvision.starter.model.lesson.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends MongoRepository<Lesson,String> {
    Optional<Lesson> findByIdAndDeletedFalse(String id);
    List<Lesson> findByTopicIdAndDeletedFalse(String topicId);

    int countByBookIdAndDeletedFalse(String bookId);
    int countByTopicIdAndDeletedFalse(String topicId);
}
