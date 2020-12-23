package tech.chaosmin.framework.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.dao.convert.KnowledgeMapper
import tech.chaosmin.framework.dao.dataobject.Knowledge
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.domain.entity.KnowledgeEntity
import tech.chaosmin.framework.handler.logic.base.BaseQueryLogic
import tech.chaosmin.framework.service.KnowledgeService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class KnowledgeQueryLogic(private val knowledgeService: KnowledgeService) : BaseQueryLogic<KnowledgeEntity, Knowledge> {

    override fun get(id: Long): KnowledgeEntity? {
        val knowledge = knowledgeService.getById(id)
        return if (knowledge == null) null
        else KnowledgeMapper.INSTANCE.convert2Entity(knowledge)
    }

    override fun page(cond: PageQuery<Knowledge>): IPage<KnowledgeEntity> {
        val page = knowledgeService.page(cond.page, cond.wrapper)
        return page.convert(KnowledgeMapper.INSTANCE::convert2Entity)
    }
}