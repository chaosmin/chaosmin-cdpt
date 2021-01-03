package tech.chaosmin.framework.module.mgmt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.entity.AuthorityEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.AuthorityMapper
import tech.chaosmin.framework.module.mgmt.service.AuthorityService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyAuthorityHandler(private val authorityService: AuthorityService) :
    AbstractTemplateOperate<AuthorityEntity, AuthorityEntity>() {
    override fun validation(arg: AuthorityEntity, result: RestResult<AuthorityEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: AuthorityEntity, result: RestResult<AuthorityEntity>): RestResult<AuthorityEntity> {
        val authority = AuthorityMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> authorityService.save(authority)
            ModifyTypeEnum.UPDATE -> authorityService.updateById(authority)
            ModifyTypeEnum.REMOVE -> authorityService.remove(Wrappers.query(authority))
        }
        return result.success(arg)
    }
}