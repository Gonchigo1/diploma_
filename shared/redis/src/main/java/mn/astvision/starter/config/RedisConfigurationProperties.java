package mn.astvision.starter.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfigurationProperties {

    private String host;
    private int port;
    private String password;

    private RedisProperties master;
    private List<RedisProperties> slaves;
}
