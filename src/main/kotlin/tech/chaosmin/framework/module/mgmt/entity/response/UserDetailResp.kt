package tech.chaosmin.framework.module.mgmt.entity.response

import tech.chaosmin.framework.module.mgmt.domain.auth.JwtUserDetails

/**
 * @author Romani min
 * @since 2020/12/15 13:02
 */
class UserDetailResp() {
    var departmentId: Long? = null
    var name: String? = null
    var roles: List<String>? = null
    var avatar: String? = null
    var introduction: String? = null

    constructor(principal: JwtUserDetails) : this() {
        this.departmentId = principal.department
        this.name = principal.username
        this.roles = principal.roles
        this.avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
        this.introduction = ""
    }
}