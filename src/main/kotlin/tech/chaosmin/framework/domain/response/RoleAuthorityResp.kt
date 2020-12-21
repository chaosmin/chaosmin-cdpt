package tech.chaosmin.framework.domain.response

import java.io.Serializable

/**
 * @author Romani min
 * @since 2020/12/21 16:54
 */
class RoleAuthorityResp : Serializable {
    var authorityId: Long? = null
    var authorityCode: String? = null
    var authorityName: String? = null
    var assigned: Boolean? = null
    var children: List<RoleAuthorityResp>? = null
}