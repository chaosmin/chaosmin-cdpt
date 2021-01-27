package tech.chaosmin.framework.module.cdpt.domain.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

enum class CustomerTypeEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    PERSON(1, "个人用户"),
    COMPANY(2, "企业");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}