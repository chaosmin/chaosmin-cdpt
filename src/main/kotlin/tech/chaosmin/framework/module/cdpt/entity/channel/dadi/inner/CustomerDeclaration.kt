package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * 大地保险健康告知 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:56
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class CustomerDeclaration {
    // 告知形式
    var declarationForm: String? = null

    // 健康告知结果
    var declarationResult: String? = null

    // 特殊告知类型
    var declarationType: String? = null

    // 健康告知内容
    var declarationDetail: String? = null
}