package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.module.cdpt.domain.enums.RateTableTypeEnum

/**
 * @author Romani min
 * @since 2020/12/30 00:55
 */
@ApiModel("产品计划费率表接口返回参数")
class PlanRateTableResp : BaseResp() {
    @ApiModelProperty(value = "责任所属计划")
    var productPlanCode: String? = null

    @ApiModelProperty(value = "费率表类型")
    var type: RateTableTypeEnum? = null

    @ApiModelProperty(value = "天数起")
    var dayStart: Int? = null

    @ApiModelProperty(value = "天数止")
    var dayEnd: Int? = null

    @ApiModelProperty(value = "计算公式")
    var formula: String? = null

    @ApiModelProperty(value = "保费")
    var premium: Double? = null

    @ApiModelProperty(value = "保费币种")
    var premiumCurrency: String? = null

    @ApiModelProperty(value = "排序")
    var sort: Int? = null
}