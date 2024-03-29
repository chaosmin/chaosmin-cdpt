package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */
@ApiModel("保险公司接口返回参数")
class PartnerResp : BaseResp() {
    @ApiModelProperty("保司编码")
    var partnerCode: String? = null

    @ApiModelProperty("保司名称")
    var partnerName: String? = null

    @ApiModelProperty("保司公钥")
    var publicKey: String? = null
}