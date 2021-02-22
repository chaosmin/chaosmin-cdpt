package tech.chaosmin.framework.configuration

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
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
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import tech.chaosmin.framework.definition.ApplicationParam
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_CACHE_EXPIRE_TIME
import tech.chaosmin.framework.definition.SystemConst.INIT_SUCCESSFULLY
import tech.chaosmin.framework.module.mgmt.domain.auth.Rule
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
        val redisTemplate = RedisTemplate<String, Rule>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = getKeySerializer()
        redisTemplate.valueSerializer = getValueSerializer()
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }

    // key 采用String序列化器
    private fun getKeySerializer(): RedisSerializer<String> {
        return StringRedisSerializer()
    }

    // value 采用Json序列化器
    private fun getValueSerializer(): RedisSerializer<Any> {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerModule(KotlinModule())
        objectMapper.registerModule(Jdk8Module())
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY)
        return GenericJackson2JsonRedisSerializer(objectMapper)
    }
}