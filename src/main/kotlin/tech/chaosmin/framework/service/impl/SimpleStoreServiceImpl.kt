package tech.chaosmin.framework.service.impl

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import tech.chaosmin.framework.domain.auth.*
import tech.chaosmin.framework.domain.auth.AnonymousAuthentication.username
import tech.chaosmin.framework.exception.AuthenticationException
import tech.chaosmin.framework.service.StoreService
import java.util.concurrent.TimeUnit

@Service
class SimpleStoreServiceImpl(private val stringRedisTemplate: StringRedisTemplate) : StoreService {
    private val adminUsers = listOf("admin", "romani")

    override fun fetchRolesByUsername(username: String): List<String> {
        return if (adminUsers.contains(username)) {
            listOf("admin")
        } else {
            listOf("reader")
        }
    }

    override fun fetchRuleWithComposeModesByRole(role: String): List<Pair<Rule, ComposeMode>> {
        return when (role) {
            "admin" -> {
                listOf(allowAllUrlRule to ComposeMode.OR, rwHttpMethodRule to ComposeMode.OR)
            }
            "reader" -> {
                listOf(allowAllUrlRule to ComposeMode.OR, roHttpMethodRule to ComposeMode.OR)
            }
            "anonymous" -> {
                listOf(anonymousRule to ComposeMode.OR)
            }
            else -> emptyList()
        }.sortedBy { (rule, _) -> rule.order }
    }

    override fun store(token: String, roles: List<String>, rolesExpr: String) {
        stringRedisTemplate.opsForValue()
            .set(KEY_PREFIX + token, "$username|$rolesExpr", EXPIRE_SECONDS, TimeUnit.SECONDS)
        stringRedisTemplate.opsForValue().set(KEY_PREFIX + username, token, EXPIRE_SECONDS, TimeUnit.SECONDS)
    }

    override fun check(token: String): LoginAuthentication {
        if (stringRedisTemplate.hasKey(KEY_PREFIX + token)) {
            val content = stringRedisTemplate.opsForValue().get(KEY_PREFIX + token)
                ?: throw AuthenticationException.INVALID_TOKEN
            val username = content.substringBefore('|')
            val roles = content.substringAfter('|').split(',')
            stringRedisTemplate.expire(KEY_PREFIX + token, EXPIRE_SECONDS, TimeUnit.SECONDS)
            stringRedisTemplate.expire(KEY_PREFIX + username, EXPIRE_SECONDS, TimeUnit.SECONDS)
            if (stringRedisTemplate.hasKey(KEY_PREFIX + token) && stringRedisTemplate.hasKey(KEY_PREFIX + username)) {
                return LoginAuthentication(username, roles, token)
            } else {
                throw AuthenticationException.INVALID_TOKEN
            }
        }
        throw AuthenticationException.INVALID_TOKEN
    }

    override fun invalid(token: String) {
        if (stringRedisTemplate.hasKey(KEY_PREFIX + token)) {
            val username = stringRedisTemplate.opsForValue().get(KEY_PREFIX + token)
            stringRedisTemplate.delete(KEY_PREFIX + token)
            stringRedisTemplate.delete(KEY_PREFIX + username)
        }
    }

    companion object {
        const val KEY_PREFIX = "base:token:"
        const val EXPIRE_SECONDS = 3 * 60 * 60L

        val allowAllUrlRule = UrlRule(10, JsonNodeFactory.instance.objectNode().apply {
            put("white_path_pattern", "/**")
        })

        val rwHttpMethodRule = HttpMethodRule(5, JsonNodeFactory.instance.objectNode().apply {
            putArray("white_list").add("GET").add("POST").add("PUT").add("DELETE")
        })

        val roHttpMethodRule = HttpMethodRule(5, JsonNodeFactory.instance.objectNode().apply {
            putArray("white_list").add("GET")
        })

        val anonymousRule = AnonymousRule(0)
    }
}