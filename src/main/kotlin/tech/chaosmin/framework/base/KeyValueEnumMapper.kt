package tech.chaosmin.framework.base

import org.mapstruct.TargetType
import tech.chaosmin.framework.utils.EnumClient

/**
 * @author Romani min
 * @since 2020/12/24 11:36
 */
class KeyValueEnumMapper {
    fun getEnumCode(enum: KeyValueEnum?): Int? {
        return enum?.getCode()
    }

    fun getEnumDesc(enum: KeyValueEnum?): String? {
        return enum?.getDesc()
    }

    fun <T> getEnum(@TargetType clazz: Class<T>, code: Int?): T? where T : Enum<T>, T : KeyValueEnum {
        return if (code == null) null
        else EnumClient.getEnum(clazz, code)
    }

    fun <T> getEnum(@TargetType clazz: Class<T>, desc: String?): T? where T : Enum<T>, T : KeyValueEnum {
        return if (desc.isNullOrBlank()) null
        else EnumClient.getEnum(clazz, desc)
    }
}