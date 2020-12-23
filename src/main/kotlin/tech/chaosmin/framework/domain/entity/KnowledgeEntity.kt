package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:56
 */
class KnowledgeEntity(id: Long? = null) : BaseEntity(id) {
    var type: Int? = null
    var knowledgeCode: String? = null
    var knowledgeText: String? = null
}