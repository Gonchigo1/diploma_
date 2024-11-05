package mn.astvision.starter.model.syslocale;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mn.astvision.starter.model.BaseEntityWithUser;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@EqualsAndHashCode(callSuper = true)
public class NameSpace extends BaseEntityWithUser {

    private String name;
    @Indexed(unique = true)
    private String value;

    private boolean active;
}

