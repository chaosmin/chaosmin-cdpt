package tech.chaosmin.framework.utils

import org.slf4j.LoggerFactory
import org.springframework.core.io.DefaultResourceLoader
import java.io.IOException
import java.util.*

class PropertiesLoader(resourcesPaths: Array<String?>) {
    private val logger = LoggerFactory.getLogger(PropertiesLoader::class.java)
    private val resourceLoader = DefaultResourceLoader()
    private var properties: Properties

    init {
        properties = loadProperties(resourcesPaths)
    }

    fun getProperties(): Properties = properties

    private fun getValue(key: String): String? {
        val systemProperty = System.getProperty(key)
        return systemProperty ?: if (properties.containsKey(key)) properties.getProperty(key) else null
    }

    fun getProperty(key: String): String {
        val value = getValue(key)
        return value ?: throw NoSuchElementException()
    }

    fun getProperty(key: String, defaultValue: String): String {
        val value = getValue(key)
        return value ?: defaultValue
    }

    private fun loadProperties(resourcesPaths: Array<String?>): Properties {
        val props = Properties()
        resourcesPaths.filterNotNull().forEach { location ->
            try {
                resourceLoader.getResource(location).inputStream.use { props.load(it) }
            } catch (ex: IOException) {
                logger.info("Could not load properties from path:" + location + ", " + ex.message)
            }
        }
        return props
    }
}