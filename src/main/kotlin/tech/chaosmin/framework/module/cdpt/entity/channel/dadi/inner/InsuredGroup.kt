package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Romani min
 * @since 2021/6/17 18:54
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class InsuredGroup {
    // 分组类型
    var insuredGroupType: String? = null

    // 户数
    var familyCount: Long? = null

    // 标的组序号
    var insuredGroupNo: Long? = null

    // 方案代码
    var planCode: String? = null

    // 份数
    var numberOfCopies: Long? = null

    // 分组人数/标的数
    var insuredCount: Long? = null
    var policyCoverageList: List<PolicyCoverage>? = null
    var guaranteeGroupList: List<GuaranteeGroup>? = null
    var policyFormList: List<PolicyForm>? = null
}