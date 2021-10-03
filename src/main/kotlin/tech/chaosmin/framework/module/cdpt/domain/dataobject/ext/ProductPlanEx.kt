/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ProductPlanEx.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.dataobject.ext

import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlan

class ProductPlanEx : ProductPlan() {
    // 保司名称
    var partnerName: String? = null

    // 产品Code
    var productCode: String? = null

    // 产品名称
    var productName: String? = null

    // 等待天数(T+N)
    var waitingDays: Int? = null
}