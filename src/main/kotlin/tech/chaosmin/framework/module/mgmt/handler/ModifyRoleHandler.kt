package tech.chaosmin.framework.module.mgmt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.entity.RoleEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.RoleMapper
import tech.chaosmin.framework.module.mgmt.service.AuthorityService
import tech.chaosmin.framework.module.mgmt.service.RoleService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyRoleHandler(
    private val roleService: RoleService,
    private val authorityService: AuthorityService
) :
    AbstractTemplateOperate<RoleEntity, RoleEntity>() {
    override fun validation(arg: RoleEntity, result: RestResult<RoleEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(arg: RoleEntity, result: RestResult<RoleEntity>): RestResult<RoleEntity> {
        val role = RoleMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                if (roleService.save(role)) {
                    setRoleAuthorities(role.id, arg.authorityIds)
                }
            }
            ModifyTypeEnum.UPDATE -> {
                if (roleService.updateById(role)) {
                    setRoleAuthorities(role.id, arg.authorityIds)
                }
            }
            ModifyTypeEnum.REMOVE -> {
                roleService.remove(Wrappers.query(role))
                role.id?.run { authorityService.clearAuthorities(this) }
            }
        }
        return result.success(arg)
    }

    private fun setRoleAuthorities(roleId: Long?, authorityIds: Set<Long>?) {
        if (roleId != null && !authorityIds.isNullOrEmpty()) {
            authorityService.updateAuthorities(roleId, authorityIds.toList())
        }
    }
}