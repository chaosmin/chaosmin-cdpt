package tech.chaosmin.framework.definition

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Romani min
 * @since 2020/11/30 20:44
 */
@Component
@ConfigurationProperties("application")
@EnableConfigurationProperties(ApplicationParam::class)
object ApplicationParam {
    var name = ""
    var version = ""
    var description = ""
    var license = ""
    var licenseUrl = ""
}