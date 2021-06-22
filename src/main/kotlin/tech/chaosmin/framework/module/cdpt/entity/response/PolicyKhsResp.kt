package tech.chaosmin.framework.module.cdpt.entity.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.base.BaseResp
import java.util.*

/**
 * @author Romani min
 * @since 2021/6/9 09:58
 */
@ApiModel("保单可回溯信息返回参数")
class PolicyKhsResp : BaseResp() {
    @ApiModelProperty("保单ID")
    var policyId: Long? = null

    @ApiModelProperty("可回溯材料类型")
    var khsType: String? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("可回溯材料生成时间")
    var fileTime: Date? = null

    @ApiModelProperty("资源地址")
    var resourceUrl: String? = null
}