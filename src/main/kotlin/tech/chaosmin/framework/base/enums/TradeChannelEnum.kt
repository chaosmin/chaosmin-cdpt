package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/9/2 10:30
 */
enum class TradeChannelEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    WECHAT(1, "微信支付"),
    ALIPAY(2, "支付宝"),
    BANKCARD(3, "银行卡");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}