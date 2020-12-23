package tech.chaosmin.framework.dao.convert.base

import org.mapstruct.Mapping
import tech.chaosmin.framework.dao.dataobject.BaseCommonDO
import tech.chaosmin.framework.domain.entity.base.BaseEntity

/**
 * @author Romani min
 * @since 2020/12/23 17:00
 */
interface BaseMapper<E : BaseEntity, D : BaseCommonDO> {
    fun convert2DO(source: E): D

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: D): E
}