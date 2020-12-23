package tech.chaosmin.framework.dao.convert.base

/**
 * @author Romani min
 * @since 2020/12/23 17:00
 */
interface BaseMapper<E, D> {
    fun convert2DO(source: E): D

    fun convert2Entity(source: D): E
}