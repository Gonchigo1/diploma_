package mn.astvision.starter.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Profile;

@Slf4j
@RequiredArgsConstructor
@Profile("production")
@Configuration
public class RedissonConfigProd {

    private final RedisConfigurationProperties properties;

    @Bean
    public RedissonClient redissonClient() {
        try {
            Set<String> slaves = new HashSet<>();
            for (RedisProperties slave : properties.getSlaves()) {
                slaves.add("redis://" + slave.getHost() + ":" + slave.getPort());
            }

            Config config = new Config();
            config
                    .useMasterSlaveServers()
                    .setMasterAddress("redis://" + properties.getMaster().getHost() + ":" + properties.getMaster().getPort())
                    .setPassword(properties.getPassword())
                    .setSlaveAddresses(slaves);
            return Redisson.create(config);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
