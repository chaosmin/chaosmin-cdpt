package tech.chaosmin.framework.module.mgmt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.base.enums.PayTypeEnum
import tech.chaosmin.framework.base.enums.UserStatusEnum

@ApiModel("用户接口返回参数")
class UserResp : BaseResp() {
    @ApiModelProperty("机构ID")
    var departmentId: Long? = null

    @ApiModelProperty("机构")
    var department: String? = null

    @ApiModelProperty("用户名")
    var username: String? = null

    @ApiModelProperty("登录名")
    var loginName: String? = null

    @ApiModelProperty("用户状态")
    var status: UserStatusEnum? = null

    @ApiModelProperty("电话")
    var phone: String? = null

    @ApiModelProperty("邮箱")
    var email: String? = null

    @ApiModelProperty("联系地址")
    var address: String? = null

    @ApiModelProperty("投保机构名称")
    var departmentName: String? = null

    @ApiModelProperty("投保机构证件号")
    var departmentCerti: String? = null

    @ApiModelProperty("支付方式")
    var payType: PayTypeEnum? = null

    @ApiModelProperty("角色ID")
    var roleIds: List<Long>? = null

    @ApiModelProperty("角色")
    var role: String? = null
}