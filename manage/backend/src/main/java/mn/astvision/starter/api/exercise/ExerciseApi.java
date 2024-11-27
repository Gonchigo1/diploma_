package mn.astvision.starter.api.exercise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.exercise.ExerciseDao;
import mn.astvision.starter.model.exercise.Exercise;
import mn.astvision.starter.repository.exercise.ExerciseRepository;
import mn.astvision.starter.service.Exercise.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/exercise")
public class ExerciseApi extends BaseController {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseService exerciseService;
    private final ExerciseDao exerciseDao;

    @PostMapping("/create")
    public ResponseEntity<?> createExerciseItem(@RequestBody Exercise exercise) {
        Exercise savedExercise = exerciseService.save(exercise);
        return ResponseEntity.ok(savedExercise);
    }

    @GetMapping()
    public ResponseEntity<?> list(
            String bookId,
            String topicId,
            String lessonId,
            String exercise,
            AntdPagination pagination) {
        try {
            AntdTableDataList<Exercise> dataList = new AntdTableDataList<>();
            pagination.setTotal(exerciseDao.count(
                    bookId,
                    topicId,
                    lessonId,
                    exercise
            ));
            dataList.setPagination(pagination);
            dataList.setList(exerciseDao.list(
                    bookId,
                    topicId,
                    lessonId,
                    exercise,
                    pagination.toPageRequest()));
            return ResponseEntity.ok(dataList);
        } catch (Exception e) {
            log.error("ERROR "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<Exercise> getById(@PathVariable String id) {
        log.debug("Fetching exercise by id: {}", id);
        return exerciseRepository.findByIdAndDeletedFalse(id)
                .map(exercise -> ResponseEntity.ok(exercise))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody Exercise updateRequest) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(updateRequest.getId());
        if (exerciseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("exercise not found.");
        }
        log.debug("Updating exercise: {}", updateRequest);
        Exercise exercise = exerciseOptional.get();
        exercise.setAudio(updateRequest.getAudio());
        exercise.setVideo(updateRequest.getVideo());
        exercise.setPdf(updateRequest.getPdf());
        exercise.setExercise(updateRequest.getExercise());
        exerciseRepository.save(exercise);

        return ResponseEntity.ok(exercise);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        exerciseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
