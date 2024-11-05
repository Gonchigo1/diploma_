package mn.astvision.starter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookType extends BaseEntityWithUser {
    private String name;
    private String code;
    private Integer order;

    private boolean active;
}

