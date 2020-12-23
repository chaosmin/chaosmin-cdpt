package tech.chaosmin.framework.domain.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.request.base.BaseReq

/**
 * @author Romani min
 * @since 2020/12/23 21:36
 */

@ApiModel("产品大类接口请求参数")
class ProductCategoryReq : BaseReq() {
    @ApiModelProperty(value = "父类ID")
    var parentId: Long? = null

    @ApiModelProperty(value = "大类代码")
    var categoryCode: String? = null

    @ApiModelProperty(value = "大类名称")
    var categoryName: String? = null

    @ApiModelProperty(value = "排序")
    var sort: Int? = null
}