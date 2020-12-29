package tech.chaosmin.framework.domain.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.enums.BasicStatusEnum
import tech.chaosmin.framework.domain.response.base.BaseResp

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */
@ApiModel("产品接口返回参数")
class ProductResp : BaseResp() {
    @ApiModelProperty(value = "保司ID")
    var partnerId: Long? = null

    @ApiModelProperty(value = "保司名称")
    var partnerName: String? = null

    @ApiModelProperty(value = "大类ID")
    var productCategoryId: Long? = null

    @ApiModelProperty(value = "产品大类")
    var categoryName: String? = null

    @ApiModelProperty(value = "产品代码")
    var productCode: String? = null

    @ApiModelProperty(value = "产品名称")
    var productName: String? = null

    @ApiModelProperty(value = "产品子名称")
    var productSubName: String? = null

    @ApiModelProperty(value = "保司产品代码")
    var partnerProductNo: String? = null

    @ApiModelProperty(value = "产品描述")
    var productDesc: String? = null

    @ApiModelProperty(value = "起保日期(T+N)")
    var waitingDays: Int? = null

    @ApiModelProperty(value = "产品状态")
    var status: BasicStatusEnum? = null

    @ApiModelProperty(value = "包含计划数")
    var numberOfPlan: Int? = null

    @ApiModelProperty(value = "投保须知")
    var externalText: String? = null
}