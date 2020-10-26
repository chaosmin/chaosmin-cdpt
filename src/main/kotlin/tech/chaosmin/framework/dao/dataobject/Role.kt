package tech.chaosmin.framework.dao.dataobject

class Role(id: Long? = null) : BaseCommonDO(id) {
    var code: String? = null
    var name: String? = null
    var priority: Int? = null
}