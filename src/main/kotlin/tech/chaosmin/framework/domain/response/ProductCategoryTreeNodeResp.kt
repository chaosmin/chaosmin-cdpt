package tech.chaosmin.framework.domain.response

import tech.chaosmin.framework.dao.dataobject.ProductCategory
import java.io.Serializable

/**
 * @author Romani min
 * @since 2020/12/24 18:28
 */
class ProductCategoryTreeNodeResp() : Serializable {
    var categoryId: Long? = null
    var label: String? = null
    var children: List<ProductCategoryTreeNodeResp>? = null

    constructor(category: ProductCategory, list: List<ProductCategory>) : this() {
        this.categoryId = category.id
        this.label = category.categoryName
        this.children = buildChildren(list)
    }

    private fun buildChildren(list: List<ProductCategory>): List<ProductCategoryTreeNodeResp> {
        return list.filter { it.parentId == categoryId }.map { ProductCategoryTreeNodeResp(it, list) }
    }
}