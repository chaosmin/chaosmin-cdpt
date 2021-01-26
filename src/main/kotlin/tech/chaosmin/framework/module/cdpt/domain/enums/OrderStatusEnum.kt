package tech.chaosmin.framework.module.cdpt.domain.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/1/26 15:49
 */
enum class OrderStatusEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    TEMP(0, "暂存"),
    SUCCESS(1, "成功"),
    CANCEL(2, "取消");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}