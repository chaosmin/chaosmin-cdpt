package tech.chaosmin.framework.domain.request.share

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

@ApiModel("部门接口请求参数")
class DepartmentShareRequestDTO : Serializable {
    @ApiModelProperty(value = "编号")
    var code: String? = null

    @ApiModelProperty(value = "名称")
    var name: String? = null
}