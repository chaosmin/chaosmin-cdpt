package tech.chaosmin.framework.domain.response

import tech.chaosmin.framework.dao.dataobject.Authority
import java.io.Serializable

/**
 * @author Romani min
 * @since 2020/12/21 16:54
 */
class RoleAuthorityResp() : Serializable {
    var authorityId: Long? = null
    var authorityCode: String? = null
    var authorityName: String? = null
    var assigned: Boolean? = null
    var children: List<RoleAuthorityResp>? = null

    constructor(authority: Authority, assigned: List<Authority>, list: List<Authority>) : this() {
        this.authorityId = authority.id
        this.authorityCode = authority.code
        this.authorityName = authority.name
        this.assigned = buildAssigned(assigned)
        this.children = buildChildren(assigned, list)
    }

    private fun buildAssigned(assigned: List<Authority>): Boolean {
        return assigned.any { it.id == this.authorityId }
    }

    private fun buildChildren(assigned: List<Authority>, list: List<Authority>): List<RoleAuthorityResp> {
        val children = list.filter { it.parentId == this.authorityId }.map { RoleAuthorityResp(it, assigned, list) }
        if (children.isNotEmpty() && children.any { it.assigned == true }) {
            this.assigned = true
        }
        return children
    }
}