package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.convert.DepartmentConvert
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.request.DepartmentReq
import tech.chaosmin.framework.domain.response.DepartmentResp
import tech.chaosmin.framework.handler.logic.DepartmentPageLogic
import tech.chaosmin.framework.service.DepartmentService
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.DepartmentShareService
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class DepartmentShareProvider(
    private val departmentService: DepartmentService,
    private val departmentPageLogic: DepartmentPageLogic
) : DepartmentShareService {
    override fun selectById(id: Long): RestResult<DepartmentResp?> {
        val department = departmentService.getById(id)
        return if (department != null) {
            val response = DepartmentConvert.INSTANCE.convert2Resp(department)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<DepartmentResp>> {
        val queryCondition = RequestUtil.getQueryCondition<Department>(request)
        val result = departmentPageLogic.run(queryCondition)
        return RestResultExt.successRestResult(result)
    }

    @Transactional
    override fun save(req: DepartmentReq): RestResult<DepartmentResp> {
        val department = DepartmentConvert.INSTANCE.convert2Entity(req)
        return if (departmentService.save(department)) {
            val response = DepartmentConvert.INSTANCE.convert2Resp(department)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun update(id: Long, req: DepartmentReq): RestResult<DepartmentResp> {
        val department = DepartmentConvert.INSTANCE.convert2Entity(req).apply { this.id = id }
        return if (departmentService.updateById(department)) {
            val response = DepartmentConvert.INSTANCE.convert2Resp(departmentService.getById(department.id))
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun delete(id: Long): RestResult<DepartmentResp> {
        return if (departmentService.removeById(id)) {
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}