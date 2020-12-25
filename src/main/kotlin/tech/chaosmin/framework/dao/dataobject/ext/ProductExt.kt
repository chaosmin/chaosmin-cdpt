package tech.chaosmin.framework.dao.dataobject.ext

import tech.chaosmin.framework.dao.dataobject.Product

/**
 * 产品扩展类
 * @author Romani min
 * @since 2020/12/25 15:58
 */
class ProductExt : Product() {
    // 产品分类ID
    var productCategoryId: Long? = null

    // 保司名称
    var partnerName: String? = null

    // 包含计划数
    var numberOfPlan: Int? = null
}