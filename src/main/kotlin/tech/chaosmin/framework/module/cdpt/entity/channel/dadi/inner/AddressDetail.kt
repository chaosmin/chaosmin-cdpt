package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * @author Romani min
 * @since 2021/6/17 18:58
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class AddressDetail {
    // 序号
    var sequenceNumber: Long? = null

    // 地区代码
    var regionCode: String? = null

    // 地址
    var fullAddress: String? = null
}