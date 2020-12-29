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

    constructor(id: Long? = null, label: String?, list: List<ProductCategory>? = null) : this() {
        this.categoryId = id
        this.label = label
        list?.run { children = buildChildren(list) }
    }

    private fun buildChildren(list: List<ProductCategory>): List<ProductCategoryTreeNodeResp> {
        return list.map {
            ProductCategoryTreeNodeResp(it.id, it.categorySubName)
        }
    }
}