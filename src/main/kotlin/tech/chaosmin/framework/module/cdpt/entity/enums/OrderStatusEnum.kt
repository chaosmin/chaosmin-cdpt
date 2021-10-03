package tech.chaosmin.framework.module.cdpt.entity.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/1/26 15:49
 */
enum class OrderStatusEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    DRAFT(-1, "草稿箱"),
    TO_BE_PAID(1, "待支付"),
    PROCESSING(2, "出单中"),
    SUCCESSFULLY_INSURED(3, "投保成功"),
    FAILED_INSURED(4, "投保失败");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}