package tech.chaosmin.framework.module.mgmt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.module.mgmt.domain.enums.UserStatusEnum

@ApiModel("用户接口返回参数")
class UserResp : BaseResp() {
    @ApiModelProperty(value = "机构ID")
    var departmentId: Long? = null

    @ApiModelProperty("机构")
    var department: String? = null

    @ApiModelProperty(value = "用户名")
    var username: String? = null

    @ApiModelProperty(value = "登录名")
    var loginName: String? = null

    @ApiModelProperty(value = "用户状态")
    var status: UserStatusEnum? = null

    @ApiModelProperty(value = "电话")
    var phone: String? = null

    @ApiModelProperty(value = "邮箱")
    var email: String? = null

    @ApiModelProperty(value = "角色ID")
    var roleId: Long? = null

    @ApiModelProperty(value = "角色")
    var role: String? = null
}