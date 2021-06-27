package tech.chaosmin.framework.module.cdpt.domain.dataobject.ext

import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy

/**
 * @author Romani min
 * @since 2021/6/27 13:55
 */
class PolicyExt : Policy() {
    // 扩展查询字段
    /** 出单人账号 */
    // var issuerLoginName: String? = null

    // 扩展返回字段
    /** 承保公司名称 */
    var partnerName: String? = null

    /** 出单人 */
    var issuerName: String? = null

    /** 投保人 */
    var holderName: String? = null
}