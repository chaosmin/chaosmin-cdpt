package tech.chaosmin.framework.domain.response.share

/**
 * @author Romani min
 * @since 2020/12/15 13:02
 */
class UserDetailShareResponseDTO() {
    var departmentId: Long? = null
    var name: String? = null
    var roles: List<String>? = null
    var avatar: String? = null
    var introduction: String? = null

    constructor(username: String) : this() {
        this.name = username
    }
}