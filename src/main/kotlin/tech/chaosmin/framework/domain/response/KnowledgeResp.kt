package tech.chaosmin.framework.domain.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.response.base.BaseResp

/**
 * @author Romani min
 * @since 2020/12/23 21:21
 */
@ApiModel("知识库接口返回参数")
class KnowledgeResp : BaseResp() {
    @ApiModelProperty(value = "条目类型")
    var type: Int? = null

    @ApiModelProperty(value = "条目编码")
    var knowledgeCode: String? = null

    @ApiModelProperty(value = "条目文本")
    var knowledgeText: String? = null
}