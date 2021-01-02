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
    @ApiModelProperty("产品ID")
    var productId: Long? = null

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

    @ApiModelProperty("状态")
    var status: BasicStatusEnum? = null
}