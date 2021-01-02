package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 18:14
 */
@ApiModel("个人产品计划接口返回参数")
class GoodsPlanResp : BaseResp() {
    @ApiModelProperty("商品ID")
    var goodsId: Long? = null

    @ApiModelProperty("用户ID")
    var userId: Long? = null

    @ApiModelProperty("产品计划ID")
    var productPlanId: Long? = null

    @ApiModelProperty("保司编码")
    var partnerCode: String? = null

    @ApiModelProperty("保司名称")
    var partnerName: String? = null

    @ApiModelProperty("一级大类")
    var categoryName: String? = null

    @ApiModelProperty("二级大类")
    var categorySubName: String? = null

    @ApiModelProperty("商品代码")
    var goodsCode: String? = null

    @ApiModelProperty("商品名称")
    var goodsName: String? = null

    @ApiModelProperty("商品状态")
    var status: Int? = null

    @ApiModelProperty("是否可售")
    var isForSale: Int? = null

    @ApiModelProperty("售卖开始时间")
    var saleStartTime: Date? = null

    @ApiModelProperty("售卖结束时间")
    var saleEndTime: Date? = null

    @ApiModelProperty("授权人")
    var authorizer: String? = null

    @ApiModelProperty("授权时间")
    var authorizeTime: Date? = null

    @ApiModelProperty("佣金比例")
    var comsRatio: Double? = null
}