package tech.chaosmin.framework.domain.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.enums.DepartmentStatusEnum
import tech.chaosmin.framework.domain.request.base.BaseReq
import java.io.Serializable

@ApiModel("部门接口请求参数")
class DepartmentReq : BaseReq() {
    @ApiModelProperty(value = "编号")
    var code: String? = null

    @ApiModelProperty(value = "名称")
    var name: String? = null

    @ApiModelProperty(value = "部门状态")
    var status: DepartmentStatusEnum? = null
}