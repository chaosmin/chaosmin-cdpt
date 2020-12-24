package tech.chaosmin.framework.domain.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import tech.chaosmin.framework.domain.enums.KnowledgeTypeEnum
import tech.chaosmin.framework.domain.request.base.BaseReq

/**
 * @author Romani min
 * @since 2020/12/23 21:21
 */
@ApiModel("知识库接口请求参数")
class KnowledgeReq : BaseReq() {
    @ApiModelProperty(value = "条目类型")
    var type: KnowledgeTypeEnum? = null

    @ApiModelProperty(value = "条目编码")
    var knowledgeCode: String? = null

    @ApiModelProperty(value = "条目文本")
    var knowledgeText: String? = null
}