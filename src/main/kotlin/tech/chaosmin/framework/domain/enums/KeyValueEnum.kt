package tech.chaosmin.framework.domain.enums

import java.io.Serializable

/**
 * @author Romani min
 * @since 2020/12/24 10:39
 */
interface KeyValueEnum : Serializable {
    fun getCode(): Int
    fun getDesc(): String
}