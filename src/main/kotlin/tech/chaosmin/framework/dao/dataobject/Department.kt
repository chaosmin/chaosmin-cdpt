package tech.chaosmin.framework.dao.dataobject

class Department(id: Long? = null) : BaseCommonDO(id) {
    var code: String? = null
    var name: String? = null
}