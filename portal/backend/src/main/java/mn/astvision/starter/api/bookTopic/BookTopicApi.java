package mn.astvision.starter.api.bookTopic;

import mn.astvision.starter.model.BookTopic;
import mn.astvision.starter.service.BookTopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/topic")
public class BookTopicApi {
    private final BookTopicService bookTopicService;
    public BookTopicApi(BookTopicService BookTopicService) {
        this.bookTopicService = BookTopicService;
    }

    @PostMapping("/create")
    public ResponseEntity<BookTopic> createTopic(@RequestBody BookTopic BookTopics) {
        BookTopic savedBook = bookTopicService.save(BookTopics);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping
    public ResponseEntity<List<BookTopic>> getAllTopic() {
        List<BookTopic> books = bookTopicService.findAll();
        return ResponseEntity.ok(books);
    }
    @GetMapping("{id}")
    public ResponseEntity<BookTopic> getTopicById(@PathVariable String id) {
        return bookTopicService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable String id) {
        bookTopicService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
