package tech.chaosmin.framework.base

import org.mapstruct.Mapping

interface IMapper<E : BaseEntity<E>, D : BaseDO> {
    fun toDO(source: E?): D?

    fun toDO(source: List<E>?): List<D>?

    @Mapping(target = "modifyType", ignore = true)
    fun toEn(source: D?): E?

    fun toEn(source: List<D>?): List<E>?
}