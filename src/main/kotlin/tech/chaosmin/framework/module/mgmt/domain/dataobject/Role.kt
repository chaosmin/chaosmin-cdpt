package tech.chaosmin.framework.module.mgmt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO

class Role(id: Long? = null) : BaseDO(id, 0) {
    // 角色编码
    var code: String? = null

    // 角色名称
    var name: String? = null

    // 优先级
    var priority: Int? = null
}