package tech.chaosmin.framework.dao.convert.base

import org.mapstruct.TargetType
import tech.chaosmin.framework.domain.enums.KeyValueEnum
import tech.chaosmin.framework.utils.EnumClient

/**
 * @author Romani min
 * @since 2020/12/24 11:36
 */
class KeyValueEnumMapper {
    fun getEnumCode(enum: KeyValueEnum?): Int? {
        return enum?.getCode()
    }

    fun <T> getEnum(@TargetType clazz: Class<T>, code: Int?): T? where T : Enum<T>, T : KeyValueEnum {
        return if (code == null) null
        else EnumClient.getEnum(clazz, code)
    }
}