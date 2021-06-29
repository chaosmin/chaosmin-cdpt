package tech.chaosmin.framework.module.cdpt.entity.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.base.enums.PolicyStatusEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/27 16:33
 */
@ApiModel("保单接口返回参数")
class PolicyResp : BaseResp() {
    @ApiModelProperty("订单号")
    var orderNo: String? = null

    @ApiModelProperty("保单号")
    var policyNo: String? = null

    @ApiModelProperty("起保时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var effectiveTime: Date? = null

    @ApiModelProperty("停保时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var expiryTime: Date? = null

    @ApiModelProperty("旅行目的地")
    var travelDestination: String? = null

    @ApiModelProperty("团单号")
    var groupNo: String? = null

    @ApiModelProperty("保单状态")
    var status: PolicyStatusEnum? = null

    @ApiModelProperty("被保人数")
    var insuredSize: Int? = null

    @ApiModelProperty("单价保费(元)")
    var unitPremium: Double? = null

    @ApiModelProperty("总保费(元)")
    var totalPremium: Double? = null

    @ApiModelProperty("实收保费(元)")
    var actualPremium: Double? = null

    @ApiModelProperty("电子保单下载地址")
    var ePolicyUrl: String? = null

    @ApiModelProperty("保险产品信息")
    var goodsPlan: GoodsPlanResp? = null

    @ApiModelProperty("投保人")
    var holder: PolicyHolderResp? = null

    @ApiModelProperty("被保人列表")
    var insuredList: List<PolicyInsurantResp>? = null
}