package tech.chaosmin.framework.module.mgmt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.mgmt.api.DepartmentShareService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.DepartmentExt
import tech.chaosmin.framework.module.mgmt.entity.DepartmentEntity
import tech.chaosmin.framework.module.mgmt.entity.request.DepartmentReq
import tech.chaosmin.framework.module.mgmt.entity.response.DepartmentResp
import tech.chaosmin.framework.module.mgmt.handler.ModifyDepartmentHandler
import tech.chaosmin.framework.module.mgmt.handler.logic.DepartmentQueryLogic
import tech.chaosmin.framework.module.mgmt.helper.convert.DepartmentConvert
import tech.chaosmin.framework.utils.RequestUtil
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

    override fun page(request: HttpServletRequest): RestResult<IPage<DepartmentResp?>> {
        val queryCondition = RequestUtil.getQueryCondition<DepartmentExt>(request)
        val page = departmentQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(DepartmentConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: DepartmentReq): RestResult<DepartmentResp> {
        val department = DepartmentConvert.INSTANCE.convert2Entity(req)
        department.save()
        return RestResultExt.execute(modifyDepartmentHandler, department, DepartmentConvert::class.java)
    }

    override fun update(id: Long, req: DepartmentReq): RestResult<DepartmentResp> {
        val department = DepartmentConvert.INSTANCE.convert2Entity(req)
        department.update(id)
        return RestResultExt.execute(modifyDepartmentHandler, department, DepartmentConvert::class.java)
    }

    override fun delete(id: Long): RestResult<DepartmentResp> {
        val department = DepartmentEntity(id)
        department.remove()
        return RestResultExt.execute(modifyDepartmentHandler, department, DepartmentConvert::class.java)
    }
}