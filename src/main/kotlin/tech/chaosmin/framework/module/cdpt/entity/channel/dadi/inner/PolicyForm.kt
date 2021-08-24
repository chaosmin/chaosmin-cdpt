package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal

/**
 * @author Romani min
 * @since 2021/6/17 18:57
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class PolicyForm {
    // 序号
    var sequenceNumber: String? = null

    // 自定义条款号[特别约定代码]
    var customFormNo: String? = null

    // 自定义条款标题
    var customFormTitle: String? = null

    // 自定义条款内容
    var customFormContent: String? = null

    // 条款分类,1：非责任类条款，2：特别约定
    var formCategory: BigDecimal? = null
}