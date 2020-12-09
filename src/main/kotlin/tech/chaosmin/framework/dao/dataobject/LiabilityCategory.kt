package tech.chaosmin.framework.dao.dataobject

/**
 * 计划责任类别
 * @author Romani min
 * @since 2020/12/8 15:57
 */
class LiabilityCategory(id: Long? = null) : BaseCommonDO(id) {
    // 父分类ID
    var parentId: Long? = null

    // 层级
    var level: Int? = null

    // 排序
    var sort: Int? = null

    // 责任类别代码
    var categoryCode: String? = null

    // 责任类型名称
    var categoryName: String? = null
}