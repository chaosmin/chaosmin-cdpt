package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.dataobject.Knowledge
import tech.chaosmin.framework.domain.entity.KnowledgeEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:18
 */
@Mapper
interface KnowledgeMapper : BaseMapper<KnowledgeEntity, Knowledge> {
    companion object {
        val INSTANCE: KnowledgeMapper = Mappers.getMapper(KnowledgeMapper::class.java)
    }
}