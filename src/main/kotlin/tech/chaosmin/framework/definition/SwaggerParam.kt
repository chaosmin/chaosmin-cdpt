package tech.chaosmin.framework.definition

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Romani min
 * @since 2020/11/30 20:44
 */
@Component
@ConfigurationProperties("swagger")
@EnableConfigurationProperties(SwaggerParam::class)
object SwaggerParam {
    var enable: Boolean = false
    var packages: String = ""
    var contact: ConTact = ConTact()

    class ConTact {
        var name: String = ""
        var url: String = ""
        var email: String = ""
        var website: String = ""
    }
}