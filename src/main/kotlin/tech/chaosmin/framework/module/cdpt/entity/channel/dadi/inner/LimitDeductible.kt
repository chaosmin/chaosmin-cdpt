package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

/**
 * @author Romani min
 * @since 2021/6/17 18:58
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class LimitDeductible {
    // 限额免赔层级（条款、责任）
    var limitDeductibleLevel: String? = null

    // 限额免赔代码
    var limitDeductibleCode: String? = null

    // 限额免赔类型
    var limitDeductibleType: String? = null

    // 限额免赔值
    var limitDeductibleValue: BigDecimal? = null

    // 限额免赔单位
    var limitDeductibleUnit: String? = null
}