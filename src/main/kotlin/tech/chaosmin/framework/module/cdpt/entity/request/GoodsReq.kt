package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 18:13
 */
@ApiModel("机构产品接口请求参数")
class GoodsReq : BaseReq() {
    @ApiModelProperty("部门ID")
    var departmentId: Long? = null

    @ApiModelProperty("产品ID")
    var productId: Long? = null

    @ApiModelProperty("产品可售集合")
    var productPlanId: String? = null

    @ApiModelProperty("状态")
    var status: Int? = null

    @ApiModelProperty("可用开始时间")
    var showStartTime: Date? = null

    @ApiModelProperty("可用结束时间")
    var showEndTime: Date? = null

    @ApiModelProperty("佣金比例")
    var comsRatio: Double? = null
}