package tech.chaosmin.framework.configuration.strategy

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.appender.FormattedLogger
import org.slf4j.LoggerFactory

/**
 * @author Romani min
 * @since 2021/4/8 16:58
 */
class P6SpyLogger : FormattedLogger() {
    private val logger = LoggerFactory.getLogger(P6SpyLogger::class.java)

    override fun logException(e: Exception?) {
        logger.error("", e)
    }

    override fun logText(text: String?) {
        logger.info(text)
    }

    override fun logSQL(connectionId: Int, now: String?, elapsed: Long, category: Category?, prepared: String?, sql: String?, url: String?) {
        val msg = strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql, url)
        if (msg.isNullOrBlank()) {
            return
        }
        when {
            Category.ERROR.equals(category) -> logger.error(msg)
            Category.WARN.equals(category) -> logger.warn(msg)
            Category.DEBUG.equals(category) -> logger.debug(msg)
            else -> logger.info(msg)
        }
    }

    override fun isCategoryEnabled(category: Category?): Boolean {
        return when {
            Category.ERROR.equals(category) -> logger.isErrorEnabled
            Category.WARN.equals(category) -> logger.isWarnEnabled
            Category.DEBUG.equals(category) -> logger.isDebugEnabled
            else -> logger.isInfoEnabled
        }
    }
}