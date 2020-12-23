package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity
import tech.chaosmin.framework.domain.enums.HttpMethodEnum

/**
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class AuthorityEntity(id: Long? = null) : BaseEntity(id) {
    var parentId: Long? = null
    var type: Int? = null
    var code: String? = null
    var name: String? = null
    var url: String? = null
    var httpMethod: HttpMethodEnum? = null
}