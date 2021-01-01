package tech.chaosmin.framework.module.mgmt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.module.mgmt.domain.enums.DepartmentStatusEnum

@ApiModel("部门接口请求参数")
class DepartmentReq : BaseReq() {
    @ApiModelProperty(value = "编号")
    var code: String? = null

    @ApiModelProperty(value = "名称")
    var name: String? = null

    @ApiModelProperty(value = "部门状态")
    var status: DepartmentStatusEnum? = null
}