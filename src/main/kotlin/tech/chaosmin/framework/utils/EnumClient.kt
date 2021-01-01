package tech.chaosmin.framework.utils

import tech.chaosmin.framework.base.KeyValueEnum

/**
 * @author Romani min
 * @since 2020/12/24 10:44
 */
@Suppress("UNCHECKED_CAST")
object EnumClient {
    fun <T> getEnumDesc(clazz: Class<T>, code: Int): String? where T : Enum<T>, T : KeyValueEnum {
        val enums = clazz.enumConstants as Array<Enum<*>>
        val var3: Array<Enum<*>> = enums
        val var4 = enums.size
        for (var5 in 0 until var4) {
            val obj = var3[var5]
            if ((obj as KeyValueEnum).getCode() == code) {
                return (obj as KeyValueEnum).getDesc()
            }
        }
        return null
    }

    fun <T> getEnum(clazz: Class<T>, code: Int): T? where T : Enum<T>, T : KeyValueEnum {
        return clazz.enumConstants.firstOrNull { it.getCode() == code }
    }
}