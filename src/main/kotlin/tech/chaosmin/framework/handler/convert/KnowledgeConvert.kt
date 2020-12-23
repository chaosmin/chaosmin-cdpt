package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.KnowledgeEntity
import tech.chaosmin.framework.domain.request.KnowledgeReq
import tech.chaosmin.framework.domain.response.KnowledgeResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

/**
 * @author Romani min
 * @since 2020/12/23 21:30
 */
@Mapper
interface KnowledgeConvert : BaseConvert<KnowledgeEntity, KnowledgeReq, KnowledgeResp> {
    companion object {
        val INSTANCE: KnowledgeConvert = Mappers.getMapper(KnowledgeConvert::class.java)
    }
}