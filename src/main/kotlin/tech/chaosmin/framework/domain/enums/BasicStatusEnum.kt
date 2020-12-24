package tech.chaosmin.framework.domain.enums

enum class BasicStatusEnum(private val code: Int, private val desc: String) : KeyValueEnum {
    ENABLED(1, "可用"),
    DISABLED(0, "不可用");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}