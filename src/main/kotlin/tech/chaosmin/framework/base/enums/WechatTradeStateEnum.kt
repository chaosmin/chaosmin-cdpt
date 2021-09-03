package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/9/2 09:58
 */
enum class WechatTradeStateEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    SUCCESS(1, "支付成功"),
    REFUND(2, "转入退款"),
    NOTPAY(3, "未支付"),
    CLOSED(4, "已关闭"),
    REVOKED(5, "已撤销（付款码支付）"),
    USERPAYING(6, "用户支付中（付款码支付）"),
    PAYERROR(7, "支付失败(其他原因，如银行返回失败)");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}