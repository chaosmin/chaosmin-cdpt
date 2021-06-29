package tech.chaosmin.framework.base.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2021/6/11 10:47
 */
enum class ChannelEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    YONG_CHENG(1, "有诚保险"),
    DADI(2, "大地保险");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}