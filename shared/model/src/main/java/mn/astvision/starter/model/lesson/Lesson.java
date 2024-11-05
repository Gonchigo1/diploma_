package mn.astvision.starter.model.lesson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.astvision.starter.model.BaseEntityWithUser;
import org.springframework.data.annotation.Transient;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends BaseEntityWithUser {
    private String bookId;
    private String topicId;
    private String lesson;
    @Transient
    private int countExercises;

}
