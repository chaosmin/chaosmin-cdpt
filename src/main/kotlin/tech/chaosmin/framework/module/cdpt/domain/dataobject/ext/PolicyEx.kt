/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PolicyEx.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.dataobject.ext

import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy

class PolicyEx : Policy() {
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