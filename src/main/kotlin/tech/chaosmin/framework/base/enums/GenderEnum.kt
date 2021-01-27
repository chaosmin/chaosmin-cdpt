package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

enum class GenderEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    FEMALE(0, "女"),
    MALE(1, "男"),
    UNKNOWN(2, "未知");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc

    companion object {
        fun getFromString(desc: String?): GenderEnum? {
            return values().firstOrNull { it.desc.equals(desc, true) }
        }
    }
}