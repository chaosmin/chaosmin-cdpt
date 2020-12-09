package tech.chaosmin.framework.dao.dataobject

/**
 * 费率表
 * @author Romani min
 * @since 2020/12/8 15:57
 */
class RateTable(id: Long? = null) : BaseCommonDO(id) {
    // 代码
    var code: String? = null

    // 名称
    var name: String? = null

    // 状态
    var remark: String? = null
}