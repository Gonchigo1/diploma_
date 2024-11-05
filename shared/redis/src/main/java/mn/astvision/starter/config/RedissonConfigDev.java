package mn.astvision.starter.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@RequiredArgsConstructor
@Profile("!production")
@Configuration
public class RedissonConfigDev {

    private final RedisConfigurationProperties properties;

    @Bean
    public RedissonClient redissonClient() {
        try {
            Config config = new Config();
            config
                    .useSingleServer()
                    .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                    .setPassword(properties.getPassword());
            return Redisson.create(config);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
