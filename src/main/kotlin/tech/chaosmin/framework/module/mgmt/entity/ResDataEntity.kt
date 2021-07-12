package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * 码表信息实体对象 <p>
 * @author Romani min
 * @since 2021/6/29 11:07
 */
class ResDataEntity(id: Long? = null) : BaseEntity<ResDataEntity>(id) {
    var itemKey: String? = null
    var itemCode: String? = null
    var itemName: String? = null
}