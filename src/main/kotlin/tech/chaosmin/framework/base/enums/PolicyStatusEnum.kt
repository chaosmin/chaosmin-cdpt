package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/1/26 15:50
 */
enum class PolicyStatusEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    SUCCESS(1, "已承保"),
    REFUND(2, "已退款");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}