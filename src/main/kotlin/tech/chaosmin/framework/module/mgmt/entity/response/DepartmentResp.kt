package tech.chaosmin.framework.module.mgmt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import tech.chaosmin.framework.base.enums.PayTypeEnum
import tech.chaosmin.framework.base.enums.StatusEnum

@ApiModel("部门接口返回参数")
class DepartmentResp : BaseResp() {
    @ApiModelProperty("编号")
    var code: String? = null

    @ApiModelProperty("名称")
    var name: String? = null

    @ApiModelProperty("部门状态")
    var status: StatusEnum? = null

    @ApiModelProperty("机构支付方式")
    var payType: PayTypeEnum? = null

    @ApiModelProperty("可用公司抬头")
    var letterHead: List<LetterHeadResp>? = null

    @ApiModelProperty("部门人数")
    var numberOfUser: Int? = null
}