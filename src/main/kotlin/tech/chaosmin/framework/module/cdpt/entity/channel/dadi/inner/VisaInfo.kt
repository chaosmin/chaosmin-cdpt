package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * 大地保险单证信息列表 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:44
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class VisaInfo {
    // 单证版本号
    var visaCode: String? = null
}