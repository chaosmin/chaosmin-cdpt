package tech.chaosmin.framework.module.cdpt.entity.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * 支付状态枚举
 *
 * @author Romani min
 * @since 2021/9/3 16:41
 */
enum class PayStatusEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    TO_BE_PAID(0, "待支付"),
    PAYING(1, "支付中"),
    PAYMENT_SUCCESSFUL(2, "支付成功"),
    PAYMENT_FAILED(3, "支付失败"),
    REFUNDED(4, "已退款");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}