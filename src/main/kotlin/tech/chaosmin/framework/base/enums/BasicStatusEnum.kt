package tech.chaosmin.framework.base.enums

import tech.chaosmin.framework.base.KeyValueEnum

enum class BasicStatusEnum(private val code: Int, private val desc: String) : KeyValueEnum {
    ENABLED(1, "可用"),
    DISABLED(0, "不可用");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}