package tech.chaosmin.framework.domain.response.share

import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2020/12/15 13:02
 */
class UserDetailShareResponseDTO {
    var departmentId: Long? = null
    var name: String? = null
    var roles: List<String>? = null
    var avatar: String? = null
    var introduction: String? = null

    init {
        val userDetails = SecurityUtil.getUserDetails()
        this.departmentId = userDetails?.departmentId
        this.name = userDetails?.name
        this.roles = userDetails?.roles
        this.avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"
        this.introduction = ""
    }
}