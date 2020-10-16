package tech.chaosmin.framework.dao.dataobject

class Department(id: Long? = null) : BaseCommonDO(id, 0) {
    var code: String? = null
    var name: String? = null
}