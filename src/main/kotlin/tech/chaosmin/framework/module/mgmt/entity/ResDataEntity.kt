package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * @author Romani min
 * @since 2021/6/29 11:07
 */
class ResDataEntity(id: Long? = null) : BaseEntity(id) {
    // 类型
    var itemKey: String? = null

    // 代码
    var itemCode: String? = null

    // 名称
    var itemName: String? = null
}