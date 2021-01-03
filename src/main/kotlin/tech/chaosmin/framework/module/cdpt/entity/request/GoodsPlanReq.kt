package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 18:13
 */
@ApiModel("个人产品计划接口请求参数")
class GoodsPlanReq : BaseReq() {
    @ApiModelProperty("售卖时间段")
    var saleDateScope: List<Date>? = null

    @ApiModelProperty("佣金比例")
    var comsRatio: Double? = null

    @ApiModelProperty("用户ID集合")
    var userIds: List<Long>? = null

    @ApiModelProperty("授权计划集合")
    var plans: Map<Long, Double>? = null
}