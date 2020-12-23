package tech.chaosmin.framework.handler.convert.base

/**
 * @author Romani min
 * @since 2020/12/23 16:31
 */
interface BaseConvert<E, RE, RQ> {
    fun convert2Resp(user: E): RQ

    fun convert2Resp(users: List<E>): List<RQ>

    fun convert2Entity(req: RE): E
}