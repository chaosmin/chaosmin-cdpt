package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * 大地保险地区信息列表 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:52
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class PolicyRegion {
    // 序号
    var sequenceNumber: Long? = null

    // 地区代码
    var regionCode: String? = null
}