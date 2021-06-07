package tech.chaosmin.framework.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyKhsEnum

import tech.chaosmin.framework.module.mgmt.domain.enums.UserStatusEnum

/**
 * @author Romani min
 * @since 2020/12/24 10:54
 */
internal class EnumClientTest {

    @Test
    fun getEnumDesc() {
        val desc = EnumClient.getEnumDesc(UserStatusEnum::class.java, 0)
        assertThat(desc).isEqualTo("初始化")
    }

    @Test
    fun getEnum() {
        val enum = EnumClient.getEnum(UserStatusEnum::class.java, 0)
        assertThat(enum).isEqualTo(UserStatusEnum.INIT)
    }

    @Test
    fun getEnumByName() {
        println(PolicyKhsEnum.values().firstOrNull { "SCHEDULE" == it.name }?.getDesc())
        println(PolicyKhsEnum.values().firstOrNull { "SSS" == it.name }?.getDesc())
    }
}