package tech.chaosmin.framework.module.mgmt.entity.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseReq
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum

@ApiModel("部门接口请求参数")
class DepartmentReq : BaseReq() {
    @ApiModelProperty("编号")
    var code: String? = null

    @ApiModelProperty("名称")
    var name: String? = null

    @ApiModelProperty("部门状态")
    var status: StatusEnum? = null

    @ApiModelProperty("机构支付方式")
    var payType: PayTypeEnum? = null

    @ApiModelProperty("可用公司抬头")
    var letterHead: List<LetterHeadReq>? = null
}