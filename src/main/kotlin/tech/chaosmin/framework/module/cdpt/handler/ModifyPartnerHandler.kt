package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.PartnerEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PartnerMapper
import tech.chaosmin.framework.module.cdpt.service.PartnerService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyPartnerHandler(private val partnerService: PartnerService) :
    AbstractTemplateOperate<PartnerEntity, PartnerEntity>() {
    override fun validation(arg: PartnerEntity, result: RestResult<PartnerEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: PartnerEntity, result: RestResult<PartnerEntity>): RestResult<PartnerEntity> {
        val partner = PartnerMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> partnerService.save(partner)
            ModifyTypeEnum.UPDATE -> partnerService.updateById(partner)
            ModifyTypeEnum.REMOVE -> partnerService.remove(Wrappers.query(partner))
        }
        return result.success(arg)
    }
}