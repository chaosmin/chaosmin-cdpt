package tech.chaosmin.framework.definition

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Romani min
 * @since 2020/12/1 19:00
 */
@Component
@ConfigurationProperties(prefix = "server.interceptor")
@EnableConfigurationProperties(ServerInterceptorParam::class)
object ServerInterceptorParam {
    var except: List<String> = mutableListOf()
}