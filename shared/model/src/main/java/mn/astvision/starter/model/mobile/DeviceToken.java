package mn.astvision.starter.model.mobile;

import lombok.*;
import mn.astvision.starter.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Sharded;

@Sharded(shardKey = {"token", "os"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeviceToken extends BaseEntity {

    public static final String IOS = "ios";
    public static final String ANDROID = "android";
    public static final String MACOS = "macos";
    public static final String WINDOWS = "windows";
    public static final String WEB = "web";

    private String os; // ios, android
    private String token; // device token
    private String deviceId; // device id

    private String email; // email
    private String ip; // IP хаяг
}
