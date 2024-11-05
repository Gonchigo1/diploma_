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
//        private List<FileData> file;

        @Transient
        private int countLessons;
        @Transient
        private int countTopics;
        @Transient
        private int countExercises;

//        private List<FileData> video ;
//        private List<FileData> audio;


//    private double price;
//    private int residual;
//    private int age;
//    private String type2;
//    private List<String> category;
//    private String writer;
//    private String drawer;
//    private String content;
//    private String size;
}
