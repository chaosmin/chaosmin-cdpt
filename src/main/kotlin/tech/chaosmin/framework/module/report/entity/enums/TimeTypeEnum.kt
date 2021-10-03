package tech.chaosmin.framework.module.report.entity.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/8/5 18:13
 */
enum class TimeTypeEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    ISSUE_TIME(1, "出单时间"),
    EFFECTIVE_TIME(2, "生效时间"),
    EXPIRY_TIME(3, "失效时间"),
    PAY_TIME(3, "支付时间");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}