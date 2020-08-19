package tech.chaosmin.framework.utils

import com.fasterxml.jackson.core.JsonGenerationException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.*
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.treeToValue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object JsonUtil {
    private val logger: Logger = LoggerFactory.getLogger(JsonUtil::class.java)

    val objectMapper = ObjectMapper().apply {
        findAndRegisterModules()
        // 允许 Java风格 注释
        configure(JsonParser.Feature.ALLOW_COMMENTS, true)
        // 允许 Yaml风格 注释
        configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true)
        // 允许单引号
        configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
        // 允许末尾逗号
        configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true)
        // 格式化输出
        configure(SerializationFeature.INDENT_OUTPUT, false)
        // 默认时间格式
        dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    }

    fun readTree(string: String?): JsonNode {
        return objectMapper.readTree(string ?: emptyObject())
    }

    inline fun <reified T> treeToValue(node: JsonNode): T? {
        return objectMapper.treeToValue<T>(node)
    }

    inline fun <reified T> convertValue(node: JsonNode): T {
        return objectMapper.convertValue(node)
    }

    fun encode(obj: Any?, prettyPrinter: Boolean = false): String {
        return if (obj == null) ""
        else try {
            if (!prettyPrinter) objectMapper.writeValueAsString(obj)
            else objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
        } catch (e: JsonGenerationException) {
            logger.error("encode(obj) error: ${e.message}", e)
            return ""
        } catch (e: JsonMappingException) {
            logger.error("encode(obj) error: ${e.message}", e)
            return ""
        } catch (e: IOException) {
            logger.error("encode(obj) error: ${e.message}", e)
            return ""
        }
    }

    fun <T> decode(json: String?, valueType: Class<T>): T? {
        if (json.isNullOrBlank()) return null
        return try {
            objectMapper.readValue(json, valueType)
        } catch (e: JsonGenerationException) {
            logger.error("decode(String, Class<T>) error: ${e.message}", e)
            null
        } catch (e: JsonMappingException) {
            logger.error("decode(String, Class<T>) error: ${e.message}", e)
            null
        } catch (e: IOException) {
            logger.error("decode(String, Class<T>) error: ${e.message}", e)
            null
        }
    }

    inline fun <reified T> parseToList(obj: Any?): List<T> {
        return if (obj == null) emptyList()
        else {
            val json = if (obj is String) obj else encode(obj)
            if (json.isBlank()) emptyList()
            else {
                val javaType = objectMapper.typeFactory.constructParametricType(List::class.java, T::class.java)
                objectMapper.readValue(json, javaType)
            }
        }
    }

    inline fun <reified T> parseToMap(obj: Any?): Map<String, T> {
        return if (obj == null) emptyMap()
        else {
            val json = if (obj is String) obj else encode(obj)
            if (json.isBlank()) emptyMap()
            else {
                val javaType =
                    objectMapper.typeFactory.constructParametricType(Map::class.java, String::class.java, T::class.java)
                objectMapper.readValue(json, javaType)
            }
        }
    }

    inline fun <reified T> parseToObj(obj: Any?): T? {
        return if (obj == null) null
        else {
            val json = if (obj is String) obj else encode(obj)
            if (json.isBlank()) null
            else objectMapper.readValue(json, object : TypeReference<T>() {})
        }
    }

    fun emptyObject(): String {
        return encode(emptyMap<String, String>())
    }

    fun emptyArray(): String {
        return encode(emptyList<String>())
    }

    fun isEmpty(json: String): Boolean {
        return json == emptyObject() || json == emptyArray()
    }

    private fun basicNode(value: Any?): JsonNode? {
        return when (value) {
            null -> null
            is String -> TextNode(value)
            is Int -> IntNode(value)
            is Long -> LongNode(value)
            is Double -> DoubleNode(value)
            is Boolean -> BooleanNode.valueOf(value)
            else -> POJONode(value)
        }
    }

    fun newObjectNode(): ObjectNode {
        return objectMapper.createObjectNode()
    }

    fun newObjectNode(key: String, value: Any?): JsonNode {
        return if (value is List<*>) {
            val a = newArrayNode(value)
            newObjectNode().set(key, a)
        } else newObjectNode().set(key, basicNode(value))
    }

    fun newArrayNode(): ArrayNode {
        return objectMapper.createArrayNode()
    }

    fun newArrayNode(value: List<*>?): ArrayNode {
        return if (value == null) newArrayNode()
        else newArrayNode().addAll(value.mapNotNull { basicNode(it) })
    }
}