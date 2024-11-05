package mn.astvision.starter.service.lesson;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.dao.lesson.LessonDao;
import mn.astvision.starter.model.lesson.Lesson;
import mn.astvision.starter.repository.exercise.ExerciseRepository;
import mn.astvision.starter.repository.lesson.LessonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonDao lessonDao;
    private final ExerciseRepository exerciseRepository;


    public Lesson save(Lesson lessonItem) {
        return lessonRepository.save(lessonItem);
    }
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> findById(String id) {
        return lessonRepository.findByIdAndDeletedFalse(id);
    }
    public void deleteById(String id) {
        lessonRepository.deleteById(id);
    }

    public Iterable<Lesson> list(
            String bookId,
            String topicId,
            String lesson,
            Pageable pageable
    ) {
        Iterable<Lesson> listData = lessonDao.list(
                bookId,
                topicId,
                lesson,
                pageable);

        for (Lesson locale : listData) {
            fillRelatedData(locale);
        }

        return listData;
    }
    public long count(
            String bookId,
            String topicId,
            String lesson
    ) {
        return lessonDao.count(bookId, topicId,lesson);
    }

    private void fillRelatedData(Lesson lesson) {
        lesson.setCountExercises(exerciseRepository.countByLessonIdAndDeletedFalse(lesson.getId()));

    }
}
