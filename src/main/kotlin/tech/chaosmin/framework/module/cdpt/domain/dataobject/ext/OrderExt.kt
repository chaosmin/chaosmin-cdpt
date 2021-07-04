package tech.chaosmin.framework.module.cdpt.domain.dataobject.ext

import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import java.util.*

/**
 * @author Romani min
 * @since 2021/7/4 16:03
 */
class OrderExt : Order() {

    // 扩展返回字段
    /** 实收保费 */
    var actualPremium: Double? = null

    /** 人数 */
    var insuredSize: Double? = null

    /** 生效时间 */
    var effectiveTime: Date? = null

    /** 止保时间 */
    var expiryTime: Date? = null

    /** 承保公司名称 */
    var partnerName: String? = null

    /** 出单时间 */
    var issueTime: Date? = null

    /** 出单人 */
    var issuer: String? = null
}