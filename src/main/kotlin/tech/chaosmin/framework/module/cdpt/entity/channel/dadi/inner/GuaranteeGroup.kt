package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * @author Romani min
 * @since 2021/6/17 18:59
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class GuaranteeGroup {
    // 保障类型
    var guaranteeType: String? = null

    // 投保类型(单人/夫妇/亲子/家庭保障)
    var applicationType: String? = null
}