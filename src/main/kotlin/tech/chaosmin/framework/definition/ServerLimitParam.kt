package tech.chaosmin.framework.definition

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Romani min
 * @since 2020/11/30 20:20
 */
@Component
@ConfigurationProperties("server.limit")
@EnableConfigurationProperties(ServerLimitParam::class)
object ServerLimitParam {
    var permitsPerSecond: Double = 1.0
    var tps: Long = 10
    var limitType = "DROP"
}