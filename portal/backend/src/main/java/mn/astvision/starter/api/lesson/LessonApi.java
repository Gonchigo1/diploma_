package mn.astvision.starter.api.lesson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.model.book.Book;
import mn.astvision.starter.model.lesson.Lesson;
import mn.astvision.starter.repository.lesson.LessonRepository;
import mn.astvision.starter.service.lesson.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/lesson")
public class LessonApi extends BaseController {

    private final LessonRepository lessonRepository;
    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<?> list(
            String bookId,
            String topicId,
            String lesson,
            AntdPagination pagination
    ) {
        AntdTableDataList<Lesson> listData = new AntdTableDataList<>();

        pagination.setTotal(
                lessonService.count(bookId, topicId,lesson)
        );

        listData.setPagination(pagination);
        listData.setList(
                lessonService.list(
                        bookId,
                        topicId,
                        lesson ,
                        pagination.toPageRequest())
        );

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?>
    select(
            String bookId,
            String topicId,
            String lesson

    ) {
        Iterable<Lesson> listData = lessonService.list(
                bookId,
                topicId,
                lesson ,
                null
        );
        return ResponseEntity.ok(listData);
    }


    @GetMapping("{id}")
    public ResponseEntity<Lesson> getById(@PathVariable String id) {
        log.debug("Fetching lesson by id: {}", id);
        return lessonRepository.findByIdAndDeletedFalse(id)
                .map(lesson -> ResponseEntity.ok(lesson))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("update")
    @Secured("ROLE_REFERENCE_DATA_MANAGE")
    public ResponseEntity<?> update(@RequestBody Lesson updateRequest) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(updateRequest.getId());
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found.");
        }

        log.debug("Updating lesson: {}", updateRequest);
        Lesson lesson = lessonOptional.get();
        lessonRepository.save(lesson);

        return ResponseEntity.ok(lesson);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        lessonService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
