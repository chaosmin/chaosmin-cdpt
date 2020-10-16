package tech.chaosmin.framework.domain.response.share

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.response.share.base.BaseShareResponseDTO

@ApiModel("用户接口返回参数")
class UserShareResponseDTO : BaseShareResponseDTO() {
    @ApiModelProperty(value = "主键")
    var id: Long? = null

    @ApiModelProperty(value = "用户名")
    var username: String? = null

    @ApiModelProperty(value = "登录名")
    var loginName: String? = null

    @ApiModelProperty(value = "电话")
    var phone: String? = null

    @ApiModelProperty(value = "邮箱")
    var email: String? = null

    @ApiModelProperty(value = "角色")
    var roles: List<String>? = null
}