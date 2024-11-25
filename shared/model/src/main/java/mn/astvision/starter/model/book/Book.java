package mn.astvision.starter.model.book;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.FileData;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntityWithUser {
        private String name;
        private String type;
        private List<FileData> image;

        @Transient
        private int countLessons;
        @Transient
        private int countTopics;
        @Transient
        private int countExercises;

}
