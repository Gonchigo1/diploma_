package mn.astvision.starter.model;
import io.jsonwebtoken.security.Password;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "teachers")
public class Teacher extends BaseEntityWithUser {
    private String school;
    private String teacherLastName;
    private String teacherFirstname;
    private String teacherEmail;
    private String teacherPhone;
    private String teacherLoginName;
    private String password;
    private boolean active;
}

