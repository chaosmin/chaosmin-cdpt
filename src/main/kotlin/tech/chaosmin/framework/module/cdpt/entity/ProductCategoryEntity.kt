package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * 产品分类信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductCategoryEntity(id: Long? = null) : BaseEntity<ProductCategoryEntity>(id) {
    var categoryName: String? = null
    var categorySubName: String? = null
    var isShow: Boolean? = null
    var sort: Int? = null
}