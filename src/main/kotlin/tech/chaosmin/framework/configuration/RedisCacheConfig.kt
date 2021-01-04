package tech.chaosmin.framework.configuration

import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.*
import tech.chaosmin.framework.definition.ApplicationParam
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_CACHE_EXPIRE_TIME
import tech.chaosmin.framework.definition.SystemConst.INIT_SUCCESSFULLY
import tech.chaosmin.framework.module.mgmt.domain.auth.Rule
import tech.chaosmin.framework.utils.JsonUtil
import java.time.Duration

/**
 * @author Romani min
 * @since 2020/12/6 15:36
 */
@Configuration
@EnableCaching
open class RedisCacheConfig(private val applicationParam: ApplicationParam) : CachingConfigurerSupport() {
    private val logger = LoggerFactory.getLogger(RedisCacheConfig::class.java)

    @Bean
    open fun cacheManager(factory: RedisConnectionFactory): CacheManager {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(DEFAULT_CACHE_EXPIRE_TIME))
            .computePrefixWith { cacheName: String -> "${applicationParam.name}:$cacheName:" }
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(getKeySerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getValueSerializer()))
            .disableCachingNullValues()
        val redisCacheManager = RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration).build()
        logger.info(INIT_SUCCESSFULLY, redisCacheManager.javaClass.name)
        return redisCacheManager
    }

    @Bean
    open fun authoritiesCacheTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Rule> {
        val objectMapper = JsonUtil.objectMapper
        val template = RedisTemplate<String, Rule>()
        template.connectionFactory = redisConnectionFactory
        val serializer = Jackson2JsonRedisSerializer(Rule::class.java)
        serializer.setObjectMapper(objectMapper)
        template.keySerializer = StringRedisSerializer()
        template.hashValueSerializer = serializer
        template.valueSerializer = serializer
        return template
    }

    // key 采用String序列化器
    private fun getKeySerializer(): RedisSerializer<String> {
        return StringRedisSerializer()
    }

    // value 采用Json序列化器
    private fun getValueSerializer(): RedisSerializer<Any> {
        return GenericJackson2JsonRedisSerializer()
    }
}