package tech.chaosmin.framework.dao.dataobject

class User(id: Long? = null) : BaseCommonDO(id, 0) {
    var username: String? = null
    var loginName: String? = null
    var password: String? = null
    var phone: String? = null
    var email: String? = null
}