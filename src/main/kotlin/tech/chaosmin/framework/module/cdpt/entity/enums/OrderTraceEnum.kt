package tech.chaosmin.framework.module.cdpt.entity.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/6/7 10:44
 */
enum class OrderTraceEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    ENTER_PAGE(0, "进入页面"),
    POLICY_NOTICE(1, "投保须知"),
    INSU_CLAUSES(2, "保险条款"),
    INSU_CONFIRM(3, "投保确认");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}