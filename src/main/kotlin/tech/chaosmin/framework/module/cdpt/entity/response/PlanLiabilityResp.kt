package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp

/**
 * @author Romani min
 * @since 2020/12/29 21:16
 */
@ApiModel("产品计划责任接口返回参数")
class PlanLiabilityResp : BaseResp() {
    @ApiModelProperty(value = "责任所属计划")
    var productPlanCode: String? = null

    @ApiModelProperty(value = "责任大类")
    var liabilityCategory: String? = null

    @ApiModelProperty(value = "责任名称")
    var liabilityName: String? = null

    @ApiModelProperty(value = "责任备注")
    var liabilityRemark: String? = null

    @ApiModelProperty(value = "排序")
    var sort: Int? = null

    @ApiModelProperty(value = "责任金额")
    var amount: String? = null

    @ApiModelProperty(value = "责任金额备注")
    var amountRemark: String? = null
}