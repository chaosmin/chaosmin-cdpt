package tech.chaosmin.framework.configuration.strategy

import com.p6spy.engine.spy.appender.MessageFormattingStrategy

/**
 * @author Romani min
 * @since 2021/4/8 16:44
 */
class P6spyLogFormatStrategy : MessageFormattingStrategy {
    /**
     * 日志格式化方式（打印SQL日志会进入此方法，耗时操作，生产环境不建议使用）
     *
     * @param connectionId: 连接ID
     * @param now:          当前时间
     * @param elapsed:      花费时间
     * @param category:     类别
     * @param prepared:     预编译SQL
     * @param sql:          最终执行的SQL
     * @param url:          数据库连接地址
     **/
    override fun formatMessage(connectionId: Int, now: String?, elapsed: Long, category: String?, prepared: String?, sql: String?, url: String?): String {
        val format = "Consume Time: %3sms, Execute SQL: %s"
        return format.format(elapsed.toString(), sql?.replace(Regex("\\s+"), " "))
    }
}