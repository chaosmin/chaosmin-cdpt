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
        this.departmentId = SecurityUtil.getDepartment()
        this.name = SecurityUtil.getUsername()
    }
}