/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ProductEx.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.dataobject.ext

import tech.chaosmin.framework.module.cdpt.domain.dataobject.Product

class ProductEx : Product() {
    // 扩展返回字段
    /** 包含计划数 */
    var numberOfPlan: Int? = null

    /** 保司编号 */
    var partnerCode: String? = null

    /** 保司名称 */
    var partnerName: String? = null

    /** 产品分类ID */
    var productCategoryId: Long? = null

    /** 产品一级大类 */
    var categoryName: String? = null

    /** 产品二级大类 */
    var categorySubName: String? = null
}