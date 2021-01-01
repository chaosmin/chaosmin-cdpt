package tech.chaosmin.framework.module.mgmt.domain.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2020/12/14 14:20
 */
enum class UserStatusEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    INIT(0, "初始化"),
    VALID(1, "可用"),
    INVALID(2, "不可用"),
    FROZEN(3, "冻结");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}