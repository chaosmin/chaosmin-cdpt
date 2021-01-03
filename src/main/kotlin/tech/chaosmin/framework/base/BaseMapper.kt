package tech.chaosmin.framework.base

import org.mapstruct.Mapping

/**
 * @author Romani min
 * @since 2020/12/23 17:00
 */
interface BaseMapper<E : BaseEntity, D : BaseDO> {
    fun convert2DO(source: E?): D?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: D?): E?
}