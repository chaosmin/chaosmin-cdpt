package tech.chaosmin.framework.module.cdpt.entity.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * 系统保单状态枚举
 *
 * @author Romani min
 * @since 2021/1/26 15:50
 */
enum class PolicyStatusEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    TO_BE_INSURED(0, "待承保"),
    INSURED(1, "已承保"),
    SURRENDERED(2, "已退保"),
    CANCELLED(3, "已取消"),
    UNDERWRITING_PASS(4, "核保通过");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}