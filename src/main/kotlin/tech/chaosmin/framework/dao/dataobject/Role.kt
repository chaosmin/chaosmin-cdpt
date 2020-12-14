package tech.chaosmin.framework.dao.dataobject

class Role(id: Long? = null) : BaseCommonDO(id, 0) {
    // 角色编码
    var code: String? = null

    // 角色名称
    var name: String? = null

    // 优先级
    var priority: Int? = null
}