package tech.chaosmin.framework.module.cdpt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */

@ApiModel("产品大类接口返回参数")
class ProductCategoryResp : BaseResp() {
    @ApiModelProperty(value = "父类名称")
    var categoryName: String? = null

    @ApiModelProperty(value = "大类名称")
    var categorySubName: String? = null

    @ApiModelProperty(value = "排序")
    var sort: Int? = null

    @ApiModelProperty(value = "是否展示")
    var isShow: Boolean? = null
}