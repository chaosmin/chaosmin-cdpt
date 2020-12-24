package tech.chaosmin.framework.domain.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.enums.BasicStatusEnum
import tech.chaosmin.framework.domain.request.base.BaseReq

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */

@ApiModel("产品接口请求参数")
class ProductReq : BaseReq() {
    @ApiModelProperty(value = "大类ID")
    var productCategoryId: Long? = null

    @ApiModelProperty(value = "保司ID")
    var partnerId: Long? = null

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

    @ApiModelProperty(value = "产品状态")
    var status: BasicStatusEnum? = null
}