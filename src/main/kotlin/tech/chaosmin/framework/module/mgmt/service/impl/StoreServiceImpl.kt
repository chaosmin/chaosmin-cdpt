package tech.chaosmin.framework.module.mgmt.service.impl

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import tech.chaosmin.framework.definition.SystemConst.APPLICATION_NAME
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_CACHE_EXPIRE_TIME
import tech.chaosmin.framework.definition.SystemConst.HTTP_METHOD
import tech.chaosmin.framework.definition.SystemConst.REQUEST_URL
import tech.chaosmin.framework.module.mgmt.domain.auth.Rule
import tech.chaosmin.framework.module.mgmt.service.StoreService
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

@Service
class StoreServiceImpl : StoreService {
    @Resource
    lateinit var redisTemplate: RedisTemplate<String, Rule>

    override fun fetchRuleWithComposeModes(authentication: Authentication): Rule {
        val username = SecurityUtil.getUsername(authentication)
        val cacheName = "$APPLICATION_NAME:users:$username:role-rule"
        if (redisTemplate.hasKey(cacheName)) {
            val cache = redisTemplate.opsForValue().get(cacheName)
            if (cache != null) return cache
        }
        synchronized(this) {
            val expr = JsonNodeFactory.instance.objectNode()
            authentication.authorities.map {
                // TODO 想一下为什么authority会包含双引号
                val permission = it.authority.replace("\"", "").split(" ")
                permission[0] to permission[1]
            }.groupBy { it.first }.forEach { (httpMethod, point) ->
                expr.with(HTTP_METHOD).putObject(httpMethod).putArray(REQUEST_URL).apply {
                    point.forEach { this.add(it.second) }
                }
            }
            return Rule(0, expr).also { store(cacheName, it) }
        }
    }

    override fun store(cacheName: String, authorities: Rule) {
        this.clear(cacheName)
        redisTemplate.opsForValue().set(cacheName, authorities, DEFAULT_CACHE_EXPIRE_TIME, TimeUnit.SECONDS)
    }

    override fun clear(cacheName: String) {
        redisTemplate.delete(cacheName)
    }
}