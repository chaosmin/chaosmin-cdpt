package tech.chaosmin.framework.domain.request.share

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

@ApiModel("角色接口请求参数")
class RoleShareRequestDTO : Serializable {
    @ApiModelProperty(value = "编号")
    var code: String? = null

    @ApiModelProperty(value = "名称")
    var name: String? = null

    @ApiModelProperty(value = "优先级")
    var priority: Int? = null

    @ApiModelProperty(value = "权限ID集合")
    var authorityIds: List<Long>? = null
}