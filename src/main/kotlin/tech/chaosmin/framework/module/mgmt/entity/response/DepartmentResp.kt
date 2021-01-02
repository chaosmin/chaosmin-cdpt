package tech.chaosmin.framework.module.mgmt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.module.mgmt.domain.enums.DepartmentStatusEnum

@ApiModel("部门接口返回参数")
class DepartmentResp : BaseResp() {
    @ApiModelProperty("编号")
    var code: String? = null

    @ApiModelProperty("名称")
    var name: String? = null

    @ApiModelProperty("部门状态")
    var status: DepartmentStatusEnum? = null

    @ApiModelProperty("部门人数")
    var numberOfUser: Int? = null
}