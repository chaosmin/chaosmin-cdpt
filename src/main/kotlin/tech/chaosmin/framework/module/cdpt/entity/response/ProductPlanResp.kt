package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.base.enums.StatusEnum

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */
@ApiModel("产品计划接口返回参数")
class ProductPlanResp : BaseResp() {
    @ApiModelProperty("产品ID")
    var productId: Long? = null

    @ApiModelProperty("保司名称")
    var partnerName: String? = null

    @ApiModelProperty("产品编码")
    var productCode: String? = null

    @ApiModelProperty("产品名称")
    var productName: String? = null

    @ApiModelProperty("计划编码")
    var planCode: String? = null

    @ApiModelProperty("计划名称")
    var planName: String? = null

    @ApiModelProperty("主险保额")
    var primaryCoverage: String? = null

    @ApiModelProperty("计划币种")
    var currency: String? = null

    @ApiModelProperty("默认佣金比例")
    var comsRatio: Double? = null

    @ApiModelProperty("佣金比例")
    var iComsRatio: Double? = null

    @ApiModelProperty("状态")
    var status: StatusEnum? = null
}