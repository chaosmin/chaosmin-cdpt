package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

enum class CustomerTypeEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    PERSON(1, "个人用户"),
    COMPANY(2, "企业用户");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}