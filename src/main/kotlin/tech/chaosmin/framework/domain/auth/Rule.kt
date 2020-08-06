package tech.chaosmin.framework.domain.auth

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.AntPathMatcher
import java.util.function.Predicate

sealed class Rule(val type: String) {
    abstract val order: Int
    abstract val expr: JsonNode

    abstract fun toPredicate(): Predicate<Action>
}

class UrlRule(override val order: Int, override val expr: JsonNode) : Rule("url") {
    private val logger: Logger = LoggerFactory.getLogger(UrlRule::class.java)

    override fun toPredicate() = Predicate<Action> { resource ->
        if (resource !is UrlAction)
            return@Predicate false

        val allowPathPattern = expr["white_path_pattern"].textValue()
        val url = resource.value
        val result = pathMatcher.match(allowPathPattern, url)
        if (result) {
            logger.debug("[rule-check] pass: pattern[{}], url[{}]", allowPathPattern, url)
        } else {
            logger.warn("[rule-check] reject: pattern[{}], url[{}]", allowPathPattern, url)
        }
        result
    }

    companion object {
        val pathMatcher = AntPathMatcher()
    }
}

class HttpMethodRule(override val order: Int, override val expr: JsonNode) : Rule("http_method") {
    private val log: Logger = LoggerFactory.getLogger(HttpMethodRule::class.java)

    override fun toPredicate() = Predicate<Action> { resource ->
        if (resource !is HttpMethodAction) {
            return@Predicate false
        }
        val allowHttpMethods = expr["white_list"].map { it.textValue().toUpperCase() }
        val method = resource.value.toUpperCase()
        val result = allowHttpMethods.contains(method)
        if (result) {
            log.debug("[rule-check] pass: white_list{}, method[{}]", allowHttpMethods, method)
        } else {
            log.warn("[rule-check] reject: white_list{}, method[{}]", allowHttpMethods, method)
        }
        result
    }
}

class AnonymousRule(override val order: Int) : Rule("anonymous") {
    private val log: Logger = LoggerFactory.getLogger(AnonymousRule::class.java)

    override val expr: JsonNode = JsonNodeFactory.instance.objectNode()

    override fun toPredicate() = Predicate<Action> {
        log.debug("[rule-check] pass.")
        true
    }
}
