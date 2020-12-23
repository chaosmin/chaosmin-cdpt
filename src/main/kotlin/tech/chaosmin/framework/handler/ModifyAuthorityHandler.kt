package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.AuthorityMapper
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.AuthorityEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.AuthorityService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyAuthorityHandler(private val authorityService: AuthorityService) :
    AbstractTemplateOperate<AuthorityEntity, AuthorityEntity>() {
    override fun validation(arg: AuthorityEntity, result: RestResult<AuthorityEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(arg: AuthorityEntity, result: RestResult<AuthorityEntity>): RestResult<AuthorityEntity> {
        val authority = AuthorityMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> authorityService.save(authority)
            ModifyTypeEnum.UPDATE -> authorityService.updateById(authority)
            ModifyTypeEnum.REMOVE -> authorityService.remove(Wrappers.query(authority))
        }
        return result.success(arg)
    }
}