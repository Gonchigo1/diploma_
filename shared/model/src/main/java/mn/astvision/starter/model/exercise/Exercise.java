package mn.astvision.starter.model.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.FileData;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exercise extends BaseEntityWithUser {
    private String bookId;
    private String topicId;
    private String lessonId;
    private String exercise;
    private List<FileData> video;
    private List<FileData> audio;
    private List<FileData> pdf;
}
