package tech.chaosmin.framework.module.mgmt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.base.enums.PayTypeEnum
import tech.chaosmin.framework.base.enums.UserStatusEnum

@ApiModel("用户接口请求参数")
class UserReq : BaseReq() {
    @ApiModelProperty("所属部门ID")
    var departmentId: Long? = null

    @ApiModelProperty("用户名")
    var username: String? = null

    @ApiModelProperty("登录名")
    var loginName: String? = null

    @ApiModelProperty("用户状态")
    var status: UserStatusEnum? = null

    @ApiModelProperty("登录密码")
    var password: String? = null

    @ApiModelProperty("电话")
    var phone: String? = null

    @ApiModelProperty("邮箱")
    var email: String? = null

    @ApiModelProperty("联系地址")
    var address: String? = null

    @ApiModelProperty("支付方式")
    var payType: PayTypeEnum? = null

    @ApiModelProperty("角色ID")
    var roleIds: List<Long>? = null
}