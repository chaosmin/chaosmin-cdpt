package tech.chaosmin.framework.module.mgmt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq

@ApiModel("角色接口请求参数")
class RoleReq : BaseReq() {
    @ApiModelProperty("编号")
    var code: String? = null

    @ApiModelProperty("名称")
    var name: String? = null

    @ApiModelProperty("优先级")
    var priority: Int? = null

    @ApiModelProperty("权限ID集合")
    var authorityIds: Set<Long>? = null
}