package tech.chaosmin.framework.module.mgmt.entity.response

import tech.chaosmin.framework.module.mgmt.domain.auth.JwtUserDetails

/**
 * @author Romani min
 * @since 2020/12/15 13:02
 */
class UserDetailResp() {
    var departmentId: Long? = null
    var userId: Long? = null
    var userName: String? = null
    var roles: List<String>? = null
    var isAdmin: Boolean = false
    var avatar: String? = null
    var introduction: String? = null
    var payType: String? = null

    constructor(principal: JwtUserDetails) : this() {
        this.departmentId = principal.departmentId
        this.userName = principal.username
        this.payType = principal.payType
        this.userId = principal.userId
        this.roles = principal.roles
        this.isAdmin = principal.roles.any { it == "administrator" }
        this.avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
        this.introduction = ""
    }
}