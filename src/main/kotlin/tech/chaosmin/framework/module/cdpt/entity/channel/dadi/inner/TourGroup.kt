package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * @author Romani min
 * @since 2021/6/17 18:59
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class TourGroup {
    // 序号
    var sequenceNumber: Long? = null

    // 前往国家或地区中文或英文
    var destinationCountryCode: String? = null

    // 前往国家或地区名称
    var travelCountryName: String? = null

    // 旅团号
    var tourGroupNo: String? = null

    // 旅游路线
    var travelRoute: String? = null

    // 邮轮号
    var cruiseNo: String? = null
}