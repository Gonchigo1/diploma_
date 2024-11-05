package mn.astvision.starter.service.book;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.dao.book.BookDao;
import mn.astvision.starter.model.book.Book;
import mn.astvision.starter.repository.book.BookRepository;
import mn.astvision.starter.repository.exercise.ExerciseRepository;
import mn.astvision.starter.repository.lesson.LessonRepository;
import mn.astvision.starter.repository.oxfordThinkers.TopicRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookDao bookDao;
    private final TopicRepository topicRepository;
    private final LessonRepository lessonRepository;
    private final ExerciseRepository exerciseRepository;



    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public Iterable<Book> list(
            String name,
            String type,
            Boolean deleted,
            Pageable pageable
    ) {
        Iterable<Book> listData = bookDao.list(
                name,
                type,
                deleted,
                pageable
        );

        for (Book book : listData) {
            fillRelatedData(book);
        }

        return listData;
    }
    public long count(
            String name,
            String type,
            Boolean deleted
    ) {
        return bookDao.count(
                name,
                type,
                deleted);
    }

    private void fillRelatedData(Book book) {
        book.setCountTopics(topicRepository.countByBookIdAndDeletedFalse(book.getId()));
        book.setCountLessons(lessonRepository.countByBookIdAndDeletedFalse(book.getId()));
        book.setCountExercises(exerciseRepository.countByBookIdAndDeletedFalse(book.getId()));
    }
}
