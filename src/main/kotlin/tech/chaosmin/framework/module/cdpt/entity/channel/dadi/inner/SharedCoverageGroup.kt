package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Romani min
 * @since 2021/6/17 18:53
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class SharedCoverageGroup {
    // 序号
    var sequenceNumber: Long? = null

    // 分组描述
    var groupDescription: String? = null
    var policyCoverageList: List<PolicyCoverage>? = null
}