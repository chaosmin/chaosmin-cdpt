package tech.chaosmin.framework.base.enums

import tech.chaosmin.framework.base.KeyValueEnum

enum class YesNoEnum(private val code: Int, private val desc: String) : KeyValueEnum {
    YES(1, "是"), NO(0, "否");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}