package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.base.enums.StatusEnum

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */
@ApiModel("产品接口返回参数")
class ProductResp : BaseResp() {
    @ApiModelProperty("保司ID")
    var partnerId: Long? = null

    @ApiModelProperty("保司名称")
    var partnerName: String? = null

    @ApiModelProperty("大类ID")
    var productCategoryId: Long? = null

    @ApiModelProperty("一级大类")
    var categoryName: String? = null

    @ApiModelProperty("二级大类")
    var categorySubName: String? = null

    @ApiModelProperty("产品代码")
    var productCode: String? = null

    @ApiModelProperty("产品名称")
    var productName: String? = null

    @ApiModelProperty("产品子名称")
    var productSubName: String? = null

    @ApiModelProperty("保司产品代码")
    var partnerProductNo: String? = null

    @ApiModelProperty("产品描述")
    var productDesc: String? = null

    @ApiModelProperty("起保日期(T+N)")
    var waitingDays: Int? = null

    @ApiModelProperty("产品状态")
    var status: StatusEnum? = null

    @ApiModelProperty("包含计划数")
    var numberOfPlan: Int? = null

    @ApiModelProperty("投保提示")
    var insuranceNotice: String? = null

    @ApiModelProperty("投保须知")
    var externalText: String? = null
}