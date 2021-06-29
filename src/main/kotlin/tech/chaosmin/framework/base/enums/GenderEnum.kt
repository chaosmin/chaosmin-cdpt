package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * 性别, 已接入res_data
 */
enum class GenderEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    UNKNOWN(0, "未知的性别"),
    MALE(1, "男性"),
    FEMALE(2, "女性"),
    NOT_STATED(3, "未说明");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}