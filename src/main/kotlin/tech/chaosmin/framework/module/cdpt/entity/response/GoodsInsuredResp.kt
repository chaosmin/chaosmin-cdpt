package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

/**
 * @author Romani min
 * @since 2021/1/20 16:17
 */
@ApiModel("个人产品计划出单接口返回参数")
class GoodsInsuredResp : Serializable {
    @ApiModelProperty("产品计划ID")
    var productPlanId: Long? = null

    @ApiModelProperty("保司编码")
    var partnerCode: String? = null

    @ApiModelProperty("保司名称")
    var partnerName: String? = null

    @ApiModelProperty("产品名称")
    var productName: String? = null

    @ApiModelProperty("商品名称")
    var productPlanName: String? = null

    @ApiModelProperty("主险保额")
    var primaryCoverage: String? = null

    @ApiModelProperty("等待期(0为即时起保)")
    var waitingDays: Int? = null

    @ApiModelProperty("佣金比例")
    var comsRatio: Double? = null

    @ApiModelProperty("产品责任")
    var goodsLiabilities: List<PlanLiabilityResp>? = null

    @ApiModelProperty("产品费率表")
    var goodsRateTable: List<PlanRateTableResp>? = null

    @ApiModelProperty("投保提示")
    var insuranceNotice: String? = null

    @ApiModelProperty("产品须知")
    var productExternal: String? = null

    @ApiModelProperty("产品条款下载地址")
    var clauseUrl: String? = null
}