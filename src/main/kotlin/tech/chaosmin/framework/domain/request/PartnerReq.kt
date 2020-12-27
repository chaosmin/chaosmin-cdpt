package tech.chaosmin.framework.domain.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.request.base.BaseReq

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */

@ApiModel("保险公司接口请求参数")
class PartnerReq : BaseReq() {
    @ApiModelProperty(value = "保司编码")
    var partnerCode: String? = null

    @ApiModelProperty(value = "保司名称")
    var productName: String? = null

    @ApiModelProperty(value = "保司公钥")
    var publicKey: String? = null
}