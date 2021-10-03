package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/9/2 20:29
 */
enum class WechatRefundStatusEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    SUCCESS(1, "退款成功"),
    CLOSED(2, "退款关闭"),
    PROCESSING(3, "退款处理中"),
    ABNORMAL(4, "退款异常");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}