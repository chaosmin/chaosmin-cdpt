package tech.chaosmin.framework.domain.auth

import com.fasterxml.jackson.databind.JsonNode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.AntPathMatcher
import tech.chaosmin.framework.domain.const.SystemConst.HTTP_METHOD
import tech.chaosmin.framework.domain.const.SystemConst.REQUEST_URL
import java.util.function.Predicate

data class Rule(val order: Int, val expr: JsonNode) {
    private val logger: Logger = LoggerFactory.getLogger(Rule::class.java)

    companion object {
        val pathMatcher = AntPathMatcher()
    }

    fun toPredicate() = Predicate<Action> { resource ->
        // anonymous 直接返回
        val url = resource.url.toUpperCase()
        val method = resource.httpMethod.toUpperCase()
        val allowHttpMethods = expr[HTTP_METHOD].textValue().toUpperCase()
        if (!allowHttpMethods.contains(method)) {
            logger.warn("[rule-check] reject: allow[{}], method[{}]", allowHttpMethods, method)
            return@Predicate false
        }
        val allowUrls = expr[REQUEST_URL].map { it.textValue().toUpperCase() }
        if (allowUrls.none { pathMatcher.match(it, url) }) {
            logger.warn("[rule-check] reject: allow[$method]{}, url[{}]", allowUrls, url)
            return@Predicate false
        }
        true
    }
}