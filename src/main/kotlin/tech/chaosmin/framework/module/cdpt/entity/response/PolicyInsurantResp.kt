package tech.chaosmin.framework.module.cdpt.entity.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.base.enums.CertiTypeEnum
import tech.chaosmin.framework.base.enums.GenderEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/27 17:28
 */
@ApiModel("被保人返回参数")
class PolicyInsurantResp : BaseResp() {
    @ApiModelProperty("姓名")
    var name: String? = null

    @ApiModelProperty("证件类型")
    var certiType: String? = null

    @ApiModelProperty("证件号码")
    var certiNo: String? = null

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var birthday: Date? = null

    @ApiModelProperty("性别")
    var gender: String? = null

    @ApiModelProperty("手机号")
    var phoneNo: String? = null
}