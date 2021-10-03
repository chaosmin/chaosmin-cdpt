package tech.chaosmin.framework.module.cdpt.entity.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * 支付方式
 *
 * @author Romani min
 * @since 2021/8/5 18:38
 */
enum class PayTypeEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    OFFLINE(0, "线下月结"),
    ONLINE(1, "线上支付");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}