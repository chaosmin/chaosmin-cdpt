package tech.chaosmin.framework.dao.dataobject

/**
 * 知识库
 * @author Romani min
 * @since 2020/12/23 11:04
 */
class Knowledge(id: Long? = null) : BaseCommonDO(id, 0) {
    // 条目类型
    var type: Int? = null

    // 条目代码
    var knowledgeCode: String? = null

    // 条目文本
    var knowledgeText: String? = null
}