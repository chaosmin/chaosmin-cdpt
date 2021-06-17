package tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner

import java.math.BigDecimal
import java.util.*

/**
 * 大地保险水滴渠道专用信息 <p>
 *
 * @author Romani min
 * @since 2021/6/17 18:49
 */
class Installment {
    // 分期序号
    var installmentPeriodSeq: Long? = null

    // 分期金额
    var installmentAmount: BigDecimal? = null

    // 应收日期
    var dueDate: Date? = null
}