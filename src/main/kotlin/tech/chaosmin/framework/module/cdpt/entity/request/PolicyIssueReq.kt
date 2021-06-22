package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.module.cdpt.domain.enums.PayMethodEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/21 18:57
 */
@ApiModel("保单出单接口请求参数")
class PolicyIssueReq : BaseReq() {
    @ApiModelProperty("订单ID")
    var orderId: Long? = null

    @ApiModelProperty("订单号")
    var orderNo: String? = null

    @ApiModelProperty("承保公司名称")
    var partnerName: String? = null

    @ApiModelProperty("产品Code")
    var productCode: String? = null

    @ApiModelProperty("产品计划ID")
    var productPlanId: Long? = null

    @ApiModelProperty("产品计划Code")
    var productPlanCode: String? = null

    @ApiModelProperty("起保时间")
    var startTime: Date? = null

    @ApiModelProperty("终止时间")
    var endTime: Date? = null

    @ApiModelProperty("保障期限")
    var days: Int? = null

    @ApiModelProperty("旅行目的地")
    var address: String? = null

    @ApiModelProperty("团单/备注")
    var remark: String? = null

    @ApiModelProperty("被保人列表")
    var insuredList: List<PolicyInsuredReq>? = null

    @ApiModelProperty("投保人姓名")
    var policyHolderName: String? = null

    @ApiModelProperty("投保人证件号")
    var policyHolderCerti: String? = null

    @ApiModelProperty("佣金比例")
    var comsRatio: Double? = null

    @ApiModelProperty("单位保费")
    var unitPremium: Double? = null

    @ApiModelProperty("总保费")
    var totalPremium: Double? = null

    @ApiModelProperty("实际保费")
    var actualPremium: Double? = null

    @ApiModelProperty("保额")
    var sa: Double? = null

    @ApiModelProperty("总保额")
    var totalSa: Double? = null

    @ApiModelProperty("支付方式")
    var payMethod: PayMethodEnum? = null

    @ApiModelProperty("可回溯信息文件集合")
    var khsUrl: List<PolicyKhsReq>? = null
}