package com.gabriel.wishlist.Config;


import com.github.fppt.jedismock.RedisServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.io.IOException;


@Configuration
@Profile("test")
public class RedissonManagerTest {

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
        redisServer = RedisServer.newRedisServer();
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() throws IOException {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @Bean
    @Primary
    public RedissonClient createRedissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(
                String.format("redis://%s:%d", redisServer.getHost(), redisServer.getBindPort())
        );
        return Redisson.create(config);
    }

}