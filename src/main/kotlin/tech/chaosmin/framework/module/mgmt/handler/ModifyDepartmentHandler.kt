package tech.chaosmin.framework.module.mgmt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.BasicStatusEnum
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.domain.dataobject.LetterHead
import tech.chaosmin.framework.module.mgmt.entity.DepartmentEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.DepartmentMapper
import tech.chaosmin.framework.module.mgmt.service.DepartmentService
import tech.chaosmin.framework.module.mgmt.service.LetterHeadService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyDepartmentHandler(
    private val departmentService: DepartmentService,
    private val letterHeadService: LetterHeadService
) : AbstractTemplateOperate<DepartmentEntity, DepartmentEntity>() {
    override fun validation(arg: DepartmentEntity, result: RestResult<DepartmentEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType")
        }
    }

    @Transactional
    override fun processor(arg: DepartmentEntity, result: RestResult<DepartmentEntity>): RestResult<DepartmentEntity> {
        val department = DepartmentMapper.INSTANCE.convert2DO(arg) ?: throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> {
                departmentService.save(department)
                if (!arg.letterhead.isNullOrEmpty()) {
                    letterHeadService.saveBatch(arg.letterhead?.map {
                        LetterHead().apply {
                            this.departmentId = department.id
                            this.title = it.title
                            this.certiNo = it.certiNo
                            this.status = BasicStatusEnum.ENABLED.getCode()
                        }
                    })
                }
            }
            ModifyTypeEnum.UPDATE -> departmentService.updateById(department)
            ModifyTypeEnum.REMOVE -> departmentService.remove(Wrappers.query(department))
        }
        return result.success(arg)
    }
}