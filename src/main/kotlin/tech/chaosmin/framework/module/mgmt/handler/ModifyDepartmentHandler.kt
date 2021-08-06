package tech.chaosmin.framework.module.mgmt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.mgmt.entity.DepartmentEntity
import tech.chaosmin.framework.module.mgmt.entity.LetterHeadEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.DepartmentMapper
import tech.chaosmin.framework.module.mgmt.helper.mapper.LetterHeadMapper
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
                saveLetterHead(department.id!!, arg.letterHead)
            }
            ModifyTypeEnum.UPDATE -> {
                departmentService.updateById(department)
                saveLetterHead(department.id!!, arg.letterHead)
            }
            ModifyTypeEnum.REMOVE -> {
                departmentService.remove(Wrappers.query(department))
                saveLetterHead(department.id!!, emptyList())
            }
        }
        return result.success(arg)
    }

    private fun saveLetterHead(departmentId: Long, list: List<LetterHeadEntity>?) {
        if (!list.isNullOrEmpty()) {
            val letterHead = letterHeadService.listByMap(mapOf("department_id" to departmentId))
            // 删除不需要的抬头
            letterHeadService.removeByIds(letterHead.filterNot { list.map { it.certiNo }.contains(it.certiNo) }.map { it.id })
            // 保存没有保存过的证件号
            letterHeadService.saveBatch(list.filterNot { letterHead.map { it.certiNo }.contains(it.certiNo) }.map {
                LetterHeadMapper.INSTANCE.convert2DO(it)?.apply {
                    this.departmentId = departmentId
                    this.status = StatusEnum.ENABLED.getCode()
                }
            })
        } else letterHeadService.realDeleteByDepartmentId(departmentId)
    }
}