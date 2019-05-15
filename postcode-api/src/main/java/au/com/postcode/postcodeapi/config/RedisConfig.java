package au.com.postcode.postcodeapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;

//@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;


    /**
     * Provides a Redis connection factory, configured according to the current environment
     * setting.
     *
     * @return configured connection factory
     */
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        final JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(redisHost);
        redisConnectionFactory.setPort(redisPort);
        redisConnectionFactory.setUsePool(true);
        return redisConnectionFactory;
    }

    /**
     * Provides a {@link RedisTemplate} that serialises keys and hashes using a string serialiser.
     *
     * @return configured template
     */
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        final Charset utf8Charset = Charset.forName("UTF-8");
        template.setKeySerializer(new StringRedisSerializer(utf8Charset));
        template.setHashKeySerializer(new StringRedisSerializer(utf8Charset));
        template.setHashValueSerializer(new StringRedisSerializer(utf8Charset));
        return template;
    }

    /**
     * Provides a Redis cache manager that is transaction-aware etc.
     *
     * @return configured cache manager
     */
    @Bean
    public RedisCacheManager cacheManager() {
        final RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.setLoadRemoteCachesOnStartup(true);
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }
}
