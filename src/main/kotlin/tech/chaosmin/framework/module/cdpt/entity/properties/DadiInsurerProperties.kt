/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * DadiInsurerProperties.kt
 *
 * Dadi insurance company interface configuration
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.entity.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = DadiInsurerProperties.DADI_CONFIGURATION_PREFIX)
class DadiInsurerProperties : PartnerProperties() {
    companion object {
        const val DADI_CONFIGURATION_PREFIX = "channel.dadi"
    }

    var channelCode: String = ""
    var channelComCode: String = ""
    var businessAttribute: String = ""
}