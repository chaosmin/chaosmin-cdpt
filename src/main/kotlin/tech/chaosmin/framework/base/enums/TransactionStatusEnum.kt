package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/8/25 13:52
 */
enum class TransactionStatusEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    WAITING_FOR_PAY(0, "初始化"),
    PAYING(1, "支付中"),
    SUCCESS(2, "支付成功"),
    FAILED(3, "支付失败");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}