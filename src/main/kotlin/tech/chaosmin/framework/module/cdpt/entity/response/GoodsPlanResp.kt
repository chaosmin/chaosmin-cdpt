package tech.chaosmin.framework.module.cdpt.entity.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.base.enums.BasicStatusEnum
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 18:14
 */
@ApiModel("个人产品计划管理接口返回参数")
open class GoodsPlanResp : BaseResp() {
    @ApiModelProperty("机构名称")
    var departmentName: String? = null

    @ApiModelProperty("用户ID")
    var userId: Long? = null

    @ApiModelProperty("用户名称")
    var userName: String? = null

    @ApiModelProperty("保司编码")
    var partnerCode: String? = null

    @ApiModelProperty("保司名称")
    var partnerName: String? = null

    @ApiModelProperty("一级大类")
    var categoryName: String? = null

    @ApiModelProperty("二级大类")
    var categorySubName: String? = null

    @ApiModelProperty("产品代码")
    var productCode: String? = null

    @ApiModelProperty("产品名称")
    var productName: String? = null

    @ApiModelProperty("产品计划ID")
    var productPlanId: Long? = null

    @ApiModelProperty("商品编码")
    var productPlanCode: String? = null

    @ApiModelProperty("商品名称")
    var productPlanName: String? = null

    @ApiModelProperty("犹豫期")
    var waitingDays: Int? = null

    @ApiModelProperty("主险保额")
    var primaryCoverage: String? = null

    @ApiModelProperty("商品状态")
    var status: BasicStatusEnum? = null

    @ApiModelProperty("是否可售")
    var forSale: BasicStatusEnum? = null

    @ApiModelProperty("售卖时间段")
    var saleDateScope: List<Date>? = null

    @ApiModelProperty("授权人")
    var authorizer: String? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("授权时间")
    var authorizeTime: Date? = null

    @ApiModelProperty("佣金比例")
    var comsRatio: Double? = null

    @ApiModelProperty("最大佣金比例")
    var maxComsRatio: Double? = null

    @ApiModelProperty("计划责任")
    var liabilities: List<PlanLiabilityResp>? = null

    @ApiModelProperty("计划费率表")
    var rateTable: List<PlanRateTableResp>? = null

    @ApiModelProperty("投保提示")
    var insuranceNotice: String? = null

    @ApiModelProperty("产品须知")
    var productExternal: String? = null
}