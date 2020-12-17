package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.convert.DepartmentConvert
import tech.chaosmin.framework.dao.dataobject.Department
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.request.share.DepartmentShareRequestDTO
import tech.chaosmin.framework.domain.response.share.DepartmentShareResponseDTO
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
    override fun selectById(id: Long): RestResult<DepartmentShareResponseDTO?> {
        val department = departmentService.getById(id)
        return if (department != null) {
            val response = DepartmentConvert.INSTANCE.convertToShareResponse(department)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.successRestResult()
        }
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<DepartmentShareResponseDTO>> {
        val queryCondition = RequestUtil.getQueryCondition<Department>(request)
        val result = departmentPageLogic.run(queryCondition)
        return RestResultExt.successRestResult(result)
    }

    @Transactional
    override fun save(requestDTO: DepartmentShareRequestDTO): RestResult<DepartmentShareResponseDTO> {
        val department = DepartmentConvert.INSTANCE.convertToBaseBean(requestDTO)
        return if (departmentService.save(department)) {
            val response = DepartmentConvert.INSTANCE.convertToShareResponse(department)
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun update(id: Long, requestDTO: DepartmentShareRequestDTO): RestResult<DepartmentShareResponseDTO> {
        val department = DepartmentConvert.INSTANCE.convertToBaseBean(requestDTO).apply { this.id = id }
        return if (departmentService.updateById(department)) {
            val response = DepartmentConvert.INSTANCE.convertToShareResponse(departmentService.getById(department.id))
            RestResultExt.successRestResult(response)
        } else {
            RestResultExt.failureRestResult()
        }
    }

    @Transactional
    override fun delete(id: Long): RestResult<DepartmentShareResponseDTO> {
        return if (departmentService.removeById(id)) {
            RestResultExt.successRestResult()
        } else {
            RestResultExt.failureRestResult()
        }
    }
}