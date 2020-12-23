package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.KnowledgeMapper
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.KnowledgeEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.KnowledgeService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyKnowledgeHandler(private val knowledgeService: KnowledgeService) :
    AbstractTemplateOperate<KnowledgeEntity, KnowledgeEntity>() {
    override fun validation(arg: KnowledgeEntity, result: RestResult<KnowledgeEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(arg: KnowledgeEntity, result: RestResult<KnowledgeEntity>): RestResult<KnowledgeEntity> {
        val knowledge = KnowledgeMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> knowledgeService.save(knowledge)
            ModifyTypeEnum.UPDATE -> knowledgeService.updateById(knowledge)
            ModifyTypeEnum.REMOVE -> knowledgeService.remove(Wrappers.query(knowledge))
        }
        return result.success(arg)
    }
}