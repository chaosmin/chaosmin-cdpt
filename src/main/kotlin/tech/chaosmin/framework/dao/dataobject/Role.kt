package tech.chaosmin.framework.dao.dataobject

class Role(id: Long? = null) : BaseCommonDO(id, 0) {
    var code: String? = null
    var name: String? = null
    var priority: Int? = null
}