package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/9/2 20:26
 */
enum class WechatRefundChannelEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    ORIGINAL(1, "原路退款"),
    BALANCE(2, "退回到余额"),
    OTHER_BALANCE(3, "原账户异常退到其他余额账户"),
    OTHER_BANKCARD(4, "原银行卡异常退到其他银行卡");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}