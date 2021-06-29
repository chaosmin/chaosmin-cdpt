package tech.chaosmin.framework.module.cdpt.entity.report

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

/**
 * 个人结算清单表
 * Personal settlement checklist
 *
 * @author Romani min
 * @since 2021/6/29 16:45
 */
class SltCheckEntity {
    // 订单号
    var orderNo: String? = null

    // 产品名称
    var goodsPlanName: String? = null

    // 人数
    var insuredSize: Int? = null

    // 标准单价
    var unitPremium: Double? = null

    // 标准保费
    var totalPremium: Double? = null

    // 实收单价
    var actualUnitPremium: Double? = null

    // 实收保费
    var actualPremium: Double? = null

    // 生效时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var effectiveTime: Date? = null

    // 终止时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    var expiryTime: Date? = null

    // 出单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var issueTime: Date? = null

    // 团号
    var groupNo: String? = null

    // 保险公司
    var partnerName: String? = null

    // 出单人
    var issuer: String? = null

    // 目的地
    var address: String? = null
}