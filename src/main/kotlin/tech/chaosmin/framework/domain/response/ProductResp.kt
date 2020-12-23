package tech.chaosmin.framework.domain.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.response.base.BaseResp

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */
@ApiModel("产品接口返回参数")
class ProductResp : BaseResp() {
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
    var status: Int? = null
}