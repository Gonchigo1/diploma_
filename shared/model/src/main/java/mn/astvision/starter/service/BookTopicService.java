package mn.astvision.starter.service;
import mn.astvision.starter.model.BookTopic;
import mn.astvision.starter.repository.BookTopicRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookTopicService {
    private final BookTopicRepository bookTopicRepository;

    public BookTopic save(BookTopic bookTopic) {
        return bookTopicRepository.save(bookTopic);}
    public List<BookTopic> findAll() {
        return bookTopicRepository.findAll();
    }
    public Optional<BookTopic> findById(String id) {
        return bookTopicRepository.findById(id);
    }
    public void deleteById(String id) {
        bookTopicRepository.deleteById(id);
    }
}
