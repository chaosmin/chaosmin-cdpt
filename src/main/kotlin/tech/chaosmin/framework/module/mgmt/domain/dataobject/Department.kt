package tech.chaosmin.framework.module.mgmt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO

open class Department(id: Long? = null) : BaseDO(id, 0) {
    // 部门编码
    var code: String? = null

    // 部门名称
    var name: String? = null

    // 部门状态
    var status: Int? = null

    // 支付方式
    var payType: Int? = null
}