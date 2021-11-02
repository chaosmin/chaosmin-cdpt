package tech.chaosmin.framework.module.cdpt.entity.request

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum
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

    @ApiModelProperty("授权产品ID")
    var goodsPlanId: Long? = null

    @ApiModelProperty("起保时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var startTime: Date? = null

    @ApiModelProperty("终止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var endTime: Date? = null

    @ApiModelProperty("保障期限")
    var days: Int? = null

    @ApiModelProperty("旅行目的地")
    var address: String? = null

    @ApiModelProperty("团单/备注")
    var groupNo: String? = null

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

    @ApiModelProperty("支付方式")
    var payType: PayTypeEnum? = null

    // 授权产品信息 后台扩展补充字段
    var goodsPlan: GoodsPlanEntity? = null

    // 投保路径
    var path: String? = null

    // 产品大类
    var categoryName: String? = null

    // 产品分类
    var categorySubName: String? = null
}