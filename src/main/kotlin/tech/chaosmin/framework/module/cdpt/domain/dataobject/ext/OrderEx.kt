/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * OrderEx.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.dataobject.ext

import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import java.util.*

class OrderEx : Order() {

    // 扩展返回字段
    /** 实收保费 */
    var actualPremium: Double? = null

    /** 投保单号 */
    var proposalNo: String? = null

    /** 人数 */
    var insuredSize: Double? = null

    /** 生效时间 */
    var effectiveTime: Date? = null

    /** 止保时间 */
    var expiryTime: Date? = null

    /** 产品名称 */
    var productName: String? = null

    /** 承保公司名称 */
    var partnerName: String? = null

    /** 出单时间 */
    var issueTime: Date? = null

    /** 出单人 */
    var issuerName: String? = null
}