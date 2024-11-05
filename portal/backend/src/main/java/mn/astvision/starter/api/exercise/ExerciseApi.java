package mn.astvision.starter.api.exercise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.model.book.Book;
import mn.astvision.starter.model.exercise.Exercise;
import mn.astvision.starter.repository.exercise.ExerciseRepository;
import mn.astvision.starter.service.Exercise.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/exercise")
public class ExerciseApi extends BaseController {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<?> list(
            String bookId,
            String topicId,
            String lessonId,
            String exercise,
            AntdPagination pagination
    ) {
        AntdTableDataList<Exercise> listData = new AntdTableDataList<>();

        pagination.setTotal(
                exerciseService.count(bookId, topicId,lessonId,exercise)
        );

        listData.setPagination(pagination);
        listData.setList(
                exerciseService.list(
                        bookId,
                        topicId,
                        lessonId,
                        exercise,
                        pagination.toPageRequest())
        );

        return ResponseEntity.ok(listData);
    }
    @GetMapping("select")
    public ResponseEntity<?>
    select(
            String bookId,
            String topicId,
            String lessonId,
            String exercise

    ) {
        Iterable<Exercise> listData = exerciseService.list(
                bookId,
                topicId,
                lessonId,
                exercise,
                null
        );
        return ResponseEntity.ok(listData);
    }


    @GetMapping("{id}")
    public ResponseEntity<Exercise> getById(@PathVariable String id) {
        log.debug("Fetching exercise by id: {}", id);
        return exerciseRepository.findByIdAndDeletedFalse(id)
                .map(exercise -> ResponseEntity.ok(exercise))
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping("update")
//    @Secured("ROLE_REFERENCE_DATA_MANAGE")
//    public ResponseEntity<?> update(@RequestBody Exercise updateRequest) {
//        Optional<Exercise> exerciseOptional = exerciseRepository.findById(updateRequest.getId());
//        if (exerciseOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("exercise not found.");
//        }
//
//        log.debug("Updating exercise: {}", updateRequest);
//        Exercise exercise = exerciseOptional.get();
//        exerciseRepository.save(exercise);
//
//        return ResponseEntity.ok(exercise);
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        exerciseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
