package tech.chaosmin.framework.domain.const

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Romani min
 * @since 2020/11/30 20:44
 */
@Component
@ConfigurationProperties("swagger")
class SwaggerParam {
    var enable: Boolean = false
    var packages: String = ""
    var contact: ConTact = ConTact()

    inner class ConTact {
        var name: String = ""
        var url: String = ""
        var email: String = ""
        var website: String = ""
    }
}