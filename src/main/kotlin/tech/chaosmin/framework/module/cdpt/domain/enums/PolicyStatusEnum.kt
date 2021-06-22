package tech.chaosmin.framework.module.cdpt.domain.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/1/26 15:50
 */
enum class PolicyStatusEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    INIT(0, "待出单"),
    SUCCESS(1, "出单成功"),
    FAILED(2, "出单失败"),
    PROCESS(3, "出单中");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}