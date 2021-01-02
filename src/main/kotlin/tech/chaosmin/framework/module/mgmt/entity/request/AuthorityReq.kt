package tech.chaosmin.framework.module.mgmt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.module.mgmt.domain.enums.HttpMethodEnum

@ApiModel("权限接口请求参数")
class AuthorityReq : BaseReq() {
    @ApiModelProperty("父权限ID")
    var parentId: Long? = null

    @ApiModelProperty("权限类型")
    var type: Int? = null

    @ApiModelProperty("编号")
    var code: String? = null

    @ApiModelProperty("名称")
    var name: String? = null

    @ApiModelProperty("访求路径")
    var url: String? = null

    @ApiModelProperty("权限")
    var httpMethod: HttpMethodEnum? = null
}