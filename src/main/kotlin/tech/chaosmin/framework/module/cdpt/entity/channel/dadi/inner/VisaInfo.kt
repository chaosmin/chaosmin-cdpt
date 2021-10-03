package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * 大地保险单证信息列表 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:44
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class VisaInfo {
    // 单证版本号
    var visaCode: String? = null
}