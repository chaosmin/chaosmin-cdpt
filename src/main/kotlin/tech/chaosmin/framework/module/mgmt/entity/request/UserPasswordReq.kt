package tech.chaosmin.framework.module.mgmt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq

@ApiModel("用户修改密码接口请求参数")
class UserPasswordReq : BaseReq() {
    @ApiModelProperty("登录密码")
    var password: String? = null

    @ApiModelProperty("新登录密码")
    var newPassword: String? = null
}