package tech.chaosmin.framework.module.mgmt.entity.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

/**
 * @author Romani min
 * @since 2021/2/18 16:35
 */
@ApiModel("抬头信息返回参数")
class LetterheadResp : Serializable {
    @ApiModelProperty("抬头名称")
    var title: String? = null

    @ApiModelProperty("抬头证件号")
    var certiNo: String? = null
}