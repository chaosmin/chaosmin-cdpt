package tech.chaosmin.framework.base.enums

import tech.chaosmin.framework.base.KeyValueEnum

enum class StatusEnum(private val code: Int, private val desc: String) : KeyValueEnum {
    DISABLED(0, "不可用"),
    ENABLED(1, "可用");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}