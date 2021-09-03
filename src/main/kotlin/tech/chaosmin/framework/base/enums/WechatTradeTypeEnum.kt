package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/9/2 09:56
 */
enum class WechatTradeTypeEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    JSAPI(0, "公众号支付"),
    NATIVE(1, "扫码支付"),
    APP(2, "APP支付"),
    MICROPAY(3, "付款码支付"),
    MWEB(4, "H5支付"),
    FACEPAY(5, "刷脸支付");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}