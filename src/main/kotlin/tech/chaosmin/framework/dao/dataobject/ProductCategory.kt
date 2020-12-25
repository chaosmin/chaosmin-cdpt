package tech.chaosmin.framework.dao.dataobject

/**
 * 产品大类
 * @author Romani min
 * @since 2020/12/23 11:07
 */
class ProductCategory(id: Long? = null) : BaseCommonDO(id, 0) {
    // 父类名称
    var parentName: String? = null

    // 大类代码
    var categoryCode: String? = null

    // 大类名称
    var categoryName: String? = null

    // 排序
    var sort: Int? = null

    // 是否展示
    var isShow: Boolean? = null
}