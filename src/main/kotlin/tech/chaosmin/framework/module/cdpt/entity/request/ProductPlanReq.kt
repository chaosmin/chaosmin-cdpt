package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.base.enums.BasicStatusEnum

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */
@ApiModel("产品计划接口请求参数")
class ProductPlanReq : BaseReq() {
    @ApiModelProperty(value = "产品ID")
    var productId: Long? = null

    @ApiModelProperty(value = "产品名称")
    var productName: String? = null

    @ApiModelProperty(value = "计划编码")
    var planCode: String? = null

    @ApiModelProperty(value = "计划名称")
    var planName: String? = null

    @ApiModelProperty(value = "主险保额")
    var primaryCoverage: String? = null

    @ApiModelProperty(value = "计划币种")
    var currency: String? = null

    @ApiModelProperty(value = "默认佣金比例")
    var defaultCommissionRatio: Double? = null

    @ApiModelProperty(value = "状态")
    var status: BasicStatusEnum? = null
}