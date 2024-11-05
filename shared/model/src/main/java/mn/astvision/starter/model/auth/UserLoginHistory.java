package mn.astvision.starter.model.auth;

import lombok.*;
import mn.astvision.starter.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Sharded;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Sharded(shardKey = {"id"})
public class UserLoginHistory extends BaseEntity {

    private String userId;
    private String deviceId; // device id
    private String ip; // IP хаяг
}
