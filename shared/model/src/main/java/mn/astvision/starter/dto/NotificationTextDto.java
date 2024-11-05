package mn.astvision.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationTextDto {

    private String title;
    private String body;
}
