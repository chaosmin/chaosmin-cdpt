package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.PartnerMapper
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.PartnerEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.PartnerService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyPartnerHandler(private val partnerService: PartnerService) :
    AbstractTemplateOperate<PartnerEntity, PartnerEntity>() {
    override fun validation(arg: PartnerEntity, result: RestResult<PartnerEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(arg: PartnerEntity, result: RestResult<PartnerEntity>): RestResult<PartnerEntity> {
        val partner = PartnerMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> partnerService.save(partner)
            ModifyTypeEnum.UPDATE -> partnerService.updateById(partner)
            ModifyTypeEnum.REMOVE -> partnerService.remove(Wrappers.query(partner))
        }
        return result.success(arg)
    }
}