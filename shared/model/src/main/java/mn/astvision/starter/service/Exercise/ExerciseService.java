package mn.astvision.starter.service.Exercise;


import lombok.RequiredArgsConstructor;
import mn.astvision.starter.dao.exercise.ExerciseDao;
import mn.astvision.starter.model.book.Book;
import mn.astvision.starter.model.exercise.Exercise;
import mn.astvision.starter.repository.exercise.ExerciseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseDao exerciseDao;


    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> findById(String id) {
        return exerciseRepository.findById(id);
    }
    public void deleteById(String id) {
        exerciseRepository.deleteById(id);
    }
    public Iterable<Exercise> list(
            String bookId,
            String topicId,
            String lessonId,
            String exercise,
            Pageable pageable
    ) {
        Iterable<Exercise> listData = exerciseDao.list(
                bookId,
                topicId,
                lessonId,
                exercise,
                pageable);

        for (Exercise locale : listData) {
            fillRelatedData(locale);
        }

        return listData;
    }
    public long count(
            String bookId,
            String topicId,
            String lessonId,
            String exercise
    ) {
        return exerciseDao.count(bookId, topicId,lessonId, exercise);
    }

    private void fillRelatedData(Exercise exercise) {
    }


}
