package tech.chaosmin.framework.domain.response.share

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.response.share.base.BaseShareResponseDTO

@ApiModel("部门接口返回参数")
class DepartmentShareResponseDTO : BaseShareResponseDTO() {
    @ApiModelProperty(value = "编号")
    var code: String? = null

    @ApiModelProperty(value = "名称")
    var name: String? = null

    @ApiModelProperty(value = "部门状态")
    var status: Int? = null
}