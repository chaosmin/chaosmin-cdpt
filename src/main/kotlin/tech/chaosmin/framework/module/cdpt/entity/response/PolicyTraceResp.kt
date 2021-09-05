package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp

/**
 * @author Romani min
 * @since 2021/6/9 09:58
 */
@ApiModel("保单可回溯信息返回参数")
class PolicyTraceResp : BaseResp() {
    @ApiModelProperty("保单ID")
    var policyId: Long? = null

    @ApiModelProperty("订单号")
    var orderNo: String? = null

    @ApiModelProperty("保单号")
    var policyNo: String? = null

    @ApiModelProperty("投保人名称")
    var holderName: String? = null

    @ApiModelProperty("出单人名称")
    var issuerName: String? = null

    @ApiModelProperty("进入页面时间")
    var enterPageTime: String? = null

    @ApiModelProperty("离开页面时间")
    var leavePageTime: String? = null

    @ApiModelProperty("阅读须知时间")
    var readTime: String? = null

    @ApiModelProperty("阅读条款时间")
    var confirmTime: String? = null

    @ApiModelProperty("确认投保时间")
    var issueTime: String? = null

    @ApiModelProperty("阅读须知截图下载地址")
    var readPicUrl: String? = null

    @ApiModelProperty("阅读条款截图下载地址")
    var confirmPicUrl: String? = null

    @ApiModelProperty("确认投保截图下载地址")
    var issuePicUrl: String? = null
}