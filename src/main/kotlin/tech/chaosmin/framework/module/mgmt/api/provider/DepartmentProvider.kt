package tech.chaosmin.framework.module.mgmt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.mgmt.api.DepartmentAPI
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.DepartmentExt
import tech.chaosmin.framework.module.mgmt.entity.DepartmentEntity
import tech.chaosmin.framework.module.mgmt.entity.request.DepartmentReq
import tech.chaosmin.framework.module.mgmt.entity.response.DepartmentResp
import tech.chaosmin.framework.module.mgmt.handler.ModifyDepartmentHandler
import tech.chaosmin.framework.module.mgmt.helper.convert.DepartmentConvert
import tech.chaosmin.framework.module.mgmt.logic.interrogator.DepartmentInterrogator
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class DepartmentProvider(
    private val departmentInterrogator: DepartmentInterrogator,
    private val modifyDepartmentHandler: ModifyDepartmentHandler
) : DepartmentAPI {
    override fun selectById(id: Long): RestResult<DepartmentResp> {
        val department = departmentInterrogator.getOne(id)
        return if (department == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(DepartmentConvert.INSTANCE.convert2Resp(department))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<DepartmentResp>> {
        val queryCondition = RequestUtil.getQueryCondition<DepartmentExt>(request)
        val page = departmentInterrogator.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(DepartmentConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: DepartmentReq): RestResult<DepartmentResp> {
        val department = DepartmentConvert.INSTANCE.convert2Entity(req).save()
        val result = modifyDepartmentHandler.operate(department)
        return RestResultExt.mapper<DepartmentResp>(result).convert {
            DepartmentConvert.INSTANCE.convert2Resp(result.data ?: DepartmentEntity())
        }
    }

    override fun update(id: Long, req: DepartmentReq): RestResult<DepartmentResp> {
        val department = DepartmentConvert.INSTANCE.convert2Entity(req).update(id)
        val result = modifyDepartmentHandler.operate(department)
        return RestResultExt.mapper<DepartmentResp>(result).convert {
            DepartmentConvert.INSTANCE.convert2Resp(result.data ?: DepartmentEntity())
        }
    }

    override fun delete(id: Long): RestResult<DepartmentResp> {
        val department = DepartmentEntity(id).remove()
        val result = modifyDepartmentHandler.operate(department)
        return RestResultExt.mapper<DepartmentResp>(result).convert {
            DepartmentConvert.INSTANCE.convert2Resp(result.data ?: DepartmentEntity())
        }
    }
}