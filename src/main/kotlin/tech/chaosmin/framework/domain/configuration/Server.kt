package tech.chaosmin.framework.domain.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "server")
@EnableConfigurationProperties(Server::class)
open class Server {
    var interceptor: Interceptor? = null
}
