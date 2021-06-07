package tech.chaosmin.framework.module.cdpt.domain.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/6/7 10:44
 */
enum class PolicyKhsEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    SCHEDULE(0, "定时截图"),
    POLICY_NOTICE(1, "投保须知"),
    INSU_CLAUSES(2, "保险条款"),
    INSU_CONFIRM(3, "投保确认");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc

    companion object {
        fun getFromString(desc: String?): PolicyKhsEnum? {
            return PolicyKhsEnum.values().firstOrNull { it.desc.equals(desc, true) }
        }
    }
}