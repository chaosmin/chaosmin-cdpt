package tech.chaosmin.framework.domain.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.enums.RateTableTypeEnum
import tech.chaosmin.framework.domain.response.base.BaseResp

/**
 * @author Romani min
 * @since 2020/12/30 00:55
 */
@ApiModel("产品计划费率表接口返回参数")
class ProductPlanRateTableResp : BaseResp() {
    @ApiModelProperty(value = "责任所属计划")
    var productPlanCode: String? = null

    @ApiModelProperty(value = "排序")
    var sort: Int? = null

    @ApiModelProperty(value = "费率表类型")
    var type: RateTableTypeEnum? = null

    @ApiModelProperty(value = "计算因子")
    var factor: String? = null

    @ApiModelProperty(value = "计算公式")
    var formula: String? = null

    @ApiModelProperty(value = "保费")
    var premium: Double? = null

    @ApiModelProperty(value = "保费币种")
    var premiumCurrency: String? = null
}