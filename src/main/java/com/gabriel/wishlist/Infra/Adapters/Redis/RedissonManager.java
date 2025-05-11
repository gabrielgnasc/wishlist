package com.gabriel.wishlist.Infra.Adapters.Redis;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "prod"})
public class RedissonManager {

    @Value("${spring.redis.connection}")
    private String redisConnection;

    @Bean
    public RedissonClient createClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redisConnection)
                .setConnectionMinimumIdleSize(5)
                .setConnectionPoolSize(10);
        return Redisson.create(config);
    }
}