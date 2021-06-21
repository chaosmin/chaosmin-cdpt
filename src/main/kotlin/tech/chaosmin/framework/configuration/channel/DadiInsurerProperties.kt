package tech.chaosmin.framework.configuration.channel

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * 大地保险配置加载类 <p>
 *
 * @author Romani min
 * @since 2021/6/21 13:27
 */
@ConfigurationProperties(prefix = DadiInsurerProperties.DADI_CONFIGURATION_PREFIX)
class DadiInsurerProperties : InsurerProperties() {
    companion object {
        const val DADI_CONFIGURATION_PREFIX = "channel.dadi"
    }
}