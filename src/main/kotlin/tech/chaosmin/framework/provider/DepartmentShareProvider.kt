package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.DepartmentEntity
import tech.chaosmin.framework.domain.entity.UserEntity
import tech.chaosmin.framework.domain.enums.ModifyTypeEnum
import tech.chaosmin.framework.domain.request.DepartmentReq
import tech.chaosmin.framework.domain.response.DepartmentResp
import tech.chaosmin.framework.handler.ModifyDepartmentHandler
import tech.chaosmin.framework.handler.convert.DepartmentConvert
import tech.chaosmin.framework.handler.logic.DepartmentQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.DepartmentShareService
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class DepartmentShareProvider(
    private val departmentQueryLogic: DepartmentQueryLogic,
    private val modifyDepartmentHandler: ModifyDepartmentHandler
) : DepartmentShareService {
    override fun selectById(id: Long): RestResult<DepartmentResp?> {
        val department = departmentQueryLogic.get(id)
        return if (department == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(DepartmentConvert.INSTANCE.convert2Resp(department))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<DepartmentResp>> {
        val queryCondition = RequestUtil.getQueryCondition<Department>(request)
        val page = departmentQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(DepartmentConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: DepartmentReq): RestResult<DepartmentResp> {
        val department = DepartmentConvert.INSTANCE.convert2Entity(req)
        department.modifyType = ModifyTypeEnum.SAVE
        return RestResultExt.execute(modifyDepartmentHandler, department, DepartmentConvert::class.java)
    }

    override fun update(id: Long, req: DepartmentReq): RestResult<DepartmentResp> {
        val department = DepartmentConvert.INSTANCE.convert2Entity(req).apply { this.id = id }
        department.modifyType = ModifyTypeEnum.UPDATE
        return RestResultExt.execute(modifyDepartmentHandler, department, DepartmentConvert::class.java)
    }

    override fun delete(id: Long): RestResult<DepartmentResp> {
        val department = DepartmentEntity(id)
        department.modifyType = ModifyTypeEnum.REMOVE
        return RestResultExt.execute(modifyDepartmentHandler, department, DepartmentConvert::class.java)
    }
}