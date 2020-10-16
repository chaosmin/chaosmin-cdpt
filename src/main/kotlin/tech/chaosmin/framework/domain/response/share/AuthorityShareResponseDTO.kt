package tech.chaosmin.framework.domain.response.share

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.enums.HttpMethodEnum
import tech.chaosmin.framework.domain.response.share.base.BaseShareResponseDTO

@ApiModel("权限接口返回参数")
class AuthorityShareResponseDTO : BaseShareResponseDTO() {
    @ApiModelProperty(value = "编号")
    var code: String? = null

    @ApiModelProperty(value = "名称")
    var name: String? = null

    @ApiModelProperty(value = "访求路径")
    var url: String? = null

    @ApiModelProperty(value = "权限")
    var httpMethod: HttpMethodEnum? = null
}