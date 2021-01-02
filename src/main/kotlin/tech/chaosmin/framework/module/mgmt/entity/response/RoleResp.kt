package tech.chaosmin.framework.module.mgmt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp

@ApiModel("角色接口返回参数")
class RoleResp : BaseResp() {
    @ApiModelProperty("编号")
    var code: String? = null

    @ApiModelProperty("名称")
    var name: String? = null

    @ApiModelProperty("优先级")
    var priority: Int? = null

    @ApiModelProperty("权限")
    var authorityIds: List<Long>? = null
}