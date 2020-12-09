package tech.chaosmin.framework.dao.dataobject

/**
 * 保险责任
 * @author Romani min
 * @since 2020/12/8 16:08
 */
class Liability(id: Long? = null) : BaseCommonDO(id) {
    // 责任分类ID
    var liabilityCategoryId: Long? = null

    // 责任代码
    var liabilityCode: String? = null

    // 责任名称
    var liabilityName: String? = null
}