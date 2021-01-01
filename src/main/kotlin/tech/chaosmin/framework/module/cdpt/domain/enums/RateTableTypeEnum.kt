package tech.chaosmin.framework.module.cdpt.domain.enums

import com.baomidou.mybatisplus.annotation.EnumValue
import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2020/12/26 17:19
 */
enum class RateTableTypeEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    FORMULA(0, "公式"),
    DAY(1, "天"),
    WEEK(2, "周"),
    MONTH(3, "月"),
    YEAR(4, "年");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}