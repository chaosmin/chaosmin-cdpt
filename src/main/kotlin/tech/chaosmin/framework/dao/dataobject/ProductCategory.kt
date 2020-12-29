package tech.chaosmin.framework.dao.dataobject

/**
 * 产品大类
 * @author Romani min
 * @since 2020/12/23 11:07
 */
class ProductCategory(id: Long? = null) : BaseCommonDO(id, 0) {
    // 父类名称
    var categoryName: String? = null

    // 大类名称
    var categorySubName: String? = null

    // 排序
    var sort: Int? = null

    // 是否展示
    var isShow: Boolean? = null

    constructor(name: String, subName: String) : this() {
        this.categoryName = name
        this.categorySubName = subName
        this.sort = 0
        this.isShow = true
    }
}