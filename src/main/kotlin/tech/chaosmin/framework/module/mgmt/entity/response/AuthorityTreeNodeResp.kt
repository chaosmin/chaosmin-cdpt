package tech.chaosmin.framework.module.mgmt.entity.response

import tech.chaosmin.framework.module.mgmt.domain.dataobject.Authority
import java.io.Serializable

/**
 * @author Romani min
 * @since 2020/12/21 16:54
 */
class AuthorityTreeNodeResp() : Serializable {
    var authorityId: Long? = null
    var label: String? = null
    var children: List<AuthorityTreeNodeResp>? = null

    constructor(authority: Authority, list: List<Authority>) : this() {
        this.authorityId = authority.id
        this.label = authority.name
        this.children = buildChildren(list)
    }

    private fun buildChildren(list: List<Authority>): List<AuthorityTreeNodeResp> {
        return list.filter { it.parentId == authorityId }.map { AuthorityTreeNodeResp(it, list) }
    }
}