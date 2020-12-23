package tech.chaosmin.framework.dao.dataobject

/**
 * 通用码表
 * @author Romani min
 * @since 2020/12/23 11:08
 */
class ResData(id: Long? = null) : BaseCommonDO(id, 0) {
    // 类型
    var itemKey: String? = null

    // 代码
    var itemCode: String? = null

    // 名称
    var itemName: String? = null

    // 描述
    var itemDesc: String? = null

    // 渠道
    var extend1: String? = null

    // 扩展字段
    var extend2: String? = null

    // 类型中文
    var extend3: String? = null
}