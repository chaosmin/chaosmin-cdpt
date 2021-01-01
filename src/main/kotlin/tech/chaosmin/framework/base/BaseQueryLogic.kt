package tech.chaosmin.framework.base

import com.baomidou.mybatisplus.core.metadata.IPage

/**
 * @author Romani min
 * @since 2020/12/23 17:05
 */
interface BaseQueryLogic<E, D> {
    fun get(id: Long): E?
    fun page(cond: PageQuery<D>): IPage<E>
}