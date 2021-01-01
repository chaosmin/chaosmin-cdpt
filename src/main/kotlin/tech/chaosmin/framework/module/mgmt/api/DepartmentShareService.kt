package tech.chaosmin.framework.module.mgmt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseShareService
import tech.chaosmin.framework.module.mgmt.entity.request.DepartmentReq
import tech.chaosmin.framework.module.mgmt.entity.response.DepartmentResp

@Api(tags = ["部门操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/departments")
interface DepartmentShareService : BaseShareService<DepartmentReq, DepartmentResp>