package tech.chaosmin.framework.dao.dataobject

import org.springframework.security.core.userdetails.UserDetails

class User(id: Long? = null) : BaseCommonDO(id, 0) {
    var username: String? = null
    var loginName: String? = null
    var password: String? = null
}