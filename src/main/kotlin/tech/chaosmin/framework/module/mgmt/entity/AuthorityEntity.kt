package tech.chaosmin.framework.module.mgmt.entity

import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.enums.HttpMethodEnum

/**
 * 权限信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class AuthorityEntity(id: Long? = null) : BaseEntity<AuthorityEntity>(id) {
    var code: String? = null
    var httpMethod: HttpMethodEnum? = null
    var name: String? = null
    var parentId: Long? = null
    var type: Int? = null
    var url: String? = null
}