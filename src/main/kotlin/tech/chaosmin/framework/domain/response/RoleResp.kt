package tech.chaosmin.framework.domain.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.response.base.BaseResp

@ApiModel("角色接口返回参数")
class RoleResp : BaseResp() {
    @ApiModelProperty(value = "编号")
    var code: String? = null

    @ApiModelProperty(value = "名称")
    var name: String? = null

    @ApiModelProperty(value = "优先级")
    var priority: Int? = null

    @ApiModelProperty(value = "权限")
    var authorities: List<String>? = null
}