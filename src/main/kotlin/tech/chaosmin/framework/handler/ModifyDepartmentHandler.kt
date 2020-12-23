package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.convert.DepartmentMapper
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.entity.DepartmentEntity
import tech.chaosmin.framework.domain.enums.ErrorCodeEnum
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.handler.base.AbstractTemplateOperate
import tech.chaosmin.framework.service.DepartmentService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyDepartmentHandler(private val departmentService: DepartmentService) :
    AbstractTemplateOperate<DepartmentEntity, DepartmentEntity>() {
    override fun validation(arg: DepartmentEntity, result: RestResult<DepartmentEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(arg: DepartmentEntity, result: RestResult<DepartmentEntity>): RestResult<DepartmentEntity> {
        val department = DepartmentMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> departmentService.save(department)
            ModifyTypeEnum.UPDATE -> departmentService.updateById(department)
            ModifyTypeEnum.REMOVE -> departmentService.remove(Wrappers.query(department))
        }
        return result.success(arg)
    }
}