package tech.chaosmin.framework.domain.const

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Romani min
 * @since 2020/11/30 20:44
 */
@Component
@ConfigurationProperties("spring.application")
open class ApplicationParam {
    var name = ""
    var version = ""
    var description = ""
    var license = ""
    var licenseUrl = ""
}