package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductCategory

/**
 * @author Romani min
 * @since 2021/1/18 15:43
 */
class GoodsCategoryEntity() {
    var id: Long? = null
    var categoryName: String? = null
    var categorySubName: String? = null

    constructor(productCategory: ProductCategory) : this() {
        this.id = productCategory.id
        this.categoryName = productCategory.categoryName
        this.categorySubName = productCategory.categorySubName
    }
}