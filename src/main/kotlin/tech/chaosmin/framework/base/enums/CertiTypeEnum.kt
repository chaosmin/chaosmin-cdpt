package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

enum class CertiTypeEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    SFZ(1, "身份证"),
    HZ(2, "护照"),
    JZ(3, "驾照");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc

    companion object {
        fun getFromString(desc: String?): CertiTypeEnum? {
            return values().firstOrNull { it.desc.equals(desc, true) }
        }
    }
}