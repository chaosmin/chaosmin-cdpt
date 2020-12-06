package tech.chaosmin.framework.service.impl

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import tech.chaosmin.framework.domain.auth.ComposeMode
import tech.chaosmin.framework.domain.auth.Rule
import tech.chaosmin.framework.domain.const.SystemConst.DEFAULT_CACHE_EXPIRE_TIME
import tech.chaosmin.framework.domain.const.SystemConst.HTTP_METHOD
import tech.chaosmin.framework.domain.const.SystemConst.REQUEST_URL
import tech.chaosmin.framework.service.StoreService
import java.util.concurrent.TimeUnit


@Service
class StoreServiceImpl(private val authoritiesCacheTemplate: RedisTemplate<String, List<Pair<Rule, ComposeMode>>>) :
    StoreService {

    override fun fetchRuleWithComposeModes(authentication: Authentication): List<Pair<Rule, ComposeMode>> {
        val username = authentication.principal.toString()
        val cache = authoritiesCacheTemplate.opsForValue().get(username)
        if (cache != null) {
            return cache
        }
        synchronized(this) {
            val rules = mutableListOf<Pair<Rule, ComposeMode>>()
            authentication.authorities.map {
                // TODO 想一下为什么authority会包含双引号
                val permission = it.authority.replace("\"", "").split(" ")
                permission[0] to permission[1]
            }.groupBy { it.first }.forEach { (httpMethod, point) ->
                rules.add(Rule(0, JsonNodeFactory.instance.objectNode().apply {
                    put(HTTP_METHOD, httpMethod)
                    putArray(REQUEST_URL).apply {
                        point.forEach { this.add(it.second) }
                    }
                }) to ComposeMode.OR)
            }
            store(username, rules)
            return rules
        }
    }

    override fun store(username: String, authorities: List<Pair<Rule, ComposeMode>>) {
        if (authorities.isEmpty()) {
            this.clear(username)
        }
        authoritiesCacheTemplate.opsForValue().set(username, authorities, DEFAULT_CACHE_EXPIRE_TIME, TimeUnit.SECONDS)
    }

    override fun clear(username: String) {
        authoritiesCacheTemplate.delete(username)
    }
}