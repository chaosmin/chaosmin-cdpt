package tech.chaosmin.framework.domain.enums

import com.baomidou.mybatisplus.annotation.EnumValue

/**
 * @author Romani min
 * @since 2020/12/24 10:25
 */
enum class KnowledgeTypeEnum(@EnumValue private val code: Int, private val desc: String) : KeyValueEnum {
    NOTICE(0, "投保须知"),
    ANNOUNCEMENT(1, "投被保人声明与授权"),
    SPECIAL_AGREEMENT(2, "特别约定"),
    DISPUTE_SETTLEMENT(3, "争议处理"),
    JURISDICTION(4, "司法管辖"),
    HEALTH_DECLARATION(5, "健康告知"),
    INSURANCE_CASE(6, "保险案例");

    override fun getCode(): Int = this.code
    override fun getDesc(): String = this.desc
}