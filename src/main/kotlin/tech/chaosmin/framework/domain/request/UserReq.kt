package tech.chaosmin.framework.domain.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.enums.UserStatusEnum
import tech.chaosmin.framework.domain.request.base.BaseReq

@ApiModel("用户接口请求参数")
class UserReq : BaseReq() {
    @ApiModelProperty(value = "所属部门ID")
    var departmentId: Long? = null

    @ApiModelProperty(value = "用户名")
    var username: String? = null

    @ApiModelProperty(value = "登录名")
    var loginName: String? = null

    @ApiModelProperty(value = "用户状态")
    var status: UserStatusEnum? = null

    @ApiModelProperty(value = "登录密码")
    var password: String? = null

    @ApiModelProperty(value = "电话")
    var phone: String? = null

    @ApiModelProperty(value = "邮箱")
    var email: String? = null

    @ApiModelProperty(value = "角色ID")
    var roleId: Long? = null
}