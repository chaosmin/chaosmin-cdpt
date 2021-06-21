package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.util.*

/**
 * 大地保险水滴渠道专用信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:49
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Installment {
    // 分期序号
    var installmentPeriodSeq: Long? = null

    // 分期金额
    var installmentAmount: BigDecimal? = null

    // 应收日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    var dueDate: Date? = null

    // installmentDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    var installmentDate: Date? = null
}