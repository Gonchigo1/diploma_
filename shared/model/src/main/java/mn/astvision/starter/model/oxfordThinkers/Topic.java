package mn.astvision.starter.model.oxfordThinkers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.lesson.Lesson;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * @author Gonching
 * Сэдэв
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends BaseEntityWithUser {
    private String name;
    private String bookId;

    @Transient
    private int countLessons;
    @Transient
    private List<Lesson> lessons;
}
