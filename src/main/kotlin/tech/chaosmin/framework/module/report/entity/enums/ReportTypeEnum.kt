/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ReportTypeEnum.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.entity.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/9/10 15:24
 */
enum class ReportTypeEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    BILLING_LIST(1, "结算清单"),
    PERSONAL_COMMISSION_SETTLEMENT(2, "个人佣金结算"),
    PAYMENT_TRANSACTION_FLOW(3, "支付交易流水"),
    BUSINESS(4, "业务系统手续费清单");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}