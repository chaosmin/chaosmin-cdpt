package tech.chaosmin.framework.domain.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.enums.HttpMethodEnum
import java.io.Serializable

@ApiModel("权限接口请求参数")
class AuthorityReq : Serializable {
    @ApiModelProperty(value = "父权限ID")
    var parentId: Long? = null

    @ApiModelProperty(value = "权限类型")
    var type: Int? = null

    @ApiModelProperty(value = "编号")
    var code: String? = null

    @ApiModelProperty(value = "名称")
    var name: String? = null

    @ApiModelProperty(value = "访求路径")
    var url: String? = null

    @ApiModelProperty(value = "权限")
    var httpMethod: HttpMethodEnum? = null
}