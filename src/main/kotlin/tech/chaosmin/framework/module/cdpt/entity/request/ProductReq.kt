package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.base.enums.BasicStatusEnum

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */
@ApiModel("产品接口请求参数")
class ProductReq : BaseReq() {
    @ApiModelProperty("大类ID")
    var productCategoryId: Long? = null

    @ApiModelProperty("保司ID")
    var partnerId: Long? = null

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
    var status: BasicStatusEnum? = null

    @ApiModelProperty("产品特约&须知")
    var externalText: String? = null
}