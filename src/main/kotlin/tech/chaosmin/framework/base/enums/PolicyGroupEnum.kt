package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * 保单团个性质
 *
 * @author Romani min
 * @since 2021/6/29 18:58
 */
enum class PolicyGroupEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    SINGLE(1, "个单"),
    GROUP(2, "团单"),
    BOTH(3, "个团兼有");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}