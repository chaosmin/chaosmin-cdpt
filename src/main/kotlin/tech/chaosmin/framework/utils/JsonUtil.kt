package tech.chaosmin.framework.utils

import com.fasterxml.jackson.core.JsonGenerationException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.type.TypeFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object JsonUtil {
    private val logger: Logger = LoggerFactory.getLogger(JsonUtil::class.java)

    private val objectMapper = ObjectMapper().apply {
        findAndRegisterModules()
        // 允许 Java风格 注释
        configure(JsonParser.Feature.ALLOW_COMMENTS, true)
        // 允许 Yaml风格 注释
        configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true)
        // 允许单引号
        configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
        // 格式化输出
        configure(SerializationFeature.INDENT_OUTPUT, false)
        // 默认时间格式
        dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
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

    fun <T> decode(json: String?, valueType: Class<T>, vararg elementClasses: Class<*>): T? {
        if (json.isNullOrBlank()) return null
        val javaType = objectMapper.typeFactory.constructParametricType(valueType, *elementClasses)
        return objectMapper.readValue(json, javaType)
    }

    fun convert2Map(json: String?): Map<String, String> {
        if (json.isNullOrBlank()) return emptyMap()
        val typeFactory = TypeFactory.defaultInstance()
        val constructMapType = typeFactory.constructMapType(HashMap::class.java, String::class.java, String::class.java)
        return objectMapper.readValue(json, constructMapType)
    }
}