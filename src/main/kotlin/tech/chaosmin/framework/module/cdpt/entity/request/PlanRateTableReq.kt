package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.base.enums.RateTableTypeEnum

/**
 * @author Romani min
 * @since 2020/12/30 00:56
 */
@ApiModel("产品计划费率表接口请求参数")
class PlanRateTableReq : BaseReq() {
    @ApiModelProperty("责任所属计划")
    var productPlanCode: String? = null

    @ApiModelProperty("费率表类型")
    var type: RateTableTypeEnum? = null

    @ApiModelProperty("天数起")
    var dayStart: Int? = null

    @ApiModelProperty("天数止")
    var dayEnd: Int? = null

    @ApiModelProperty("保费")
    var premium: Double? = null

    @ApiModelProperty("保费币种")
    var premiumCurrency: String? = null

    @ApiModelProperty("排序")
    var sort: Int? = null
}