package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/21 18:53
 */
@ApiModel("保单被保人接口请求参数")
class PolicyInsuredReq : BaseReq() {
    @ApiModelProperty("证件类型")
    var certiType: String? = null

    @ApiModelProperty("性别")
    var gender: String? = null

    @ApiModelProperty("手机号码")
    var mobile: String? = null

    @ApiModelProperty("证件号")
    var certiNo: String? = null

    @ApiModelProperty("生日")
    var dateOfBirth: Date? = null

    @ApiModelProperty("姓名")
    var name: String? = null
}