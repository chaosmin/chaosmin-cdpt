package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * 保险计划责任信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/26 12:55
 */
class ProductPlanLibEntity(id: Long? = null) : BaseEntity<ProductPlanLibEntity>(id) {
    var amount: String? = null
    var amountRemark: String? = null
    var liabilityCategory: String? = null
    var liabilityName: String? = null
    var liabilityRemark: String? = null
    var productPlanCode: String? = null
    var productPlanId: Long? = null
    var sort: Int? = null
}