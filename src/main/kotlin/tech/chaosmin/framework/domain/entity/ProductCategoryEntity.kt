package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductCategoryEntity(id: Long? = null) : BaseEntity(id) {
    var categoryName: String? = null
    var categorySubName: String? = null
    var sort: Int? = null
    var isShow: Boolean? = null
}