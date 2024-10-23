package de.sopracss.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean(name = "cacheManager")
    @ConditionalOnProperty(name = "demo.cache.redis.enabled", havingValue = "false", matchIfMissing = true)
    // fallback inMemCache
    public CacheManager inMemCacheManager() {
        return new ConcurrentMapCacheManager("product");
    }

    @Bean(name = "cacheManager")
    @ConditionalOnProperty(name = "demo.cache.redis.enabled", havingValue = "true", matchIfMissing = false)
    RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24)))
                .withCacheConfiguration(
                        "product",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)))
                .build();
    }

}
