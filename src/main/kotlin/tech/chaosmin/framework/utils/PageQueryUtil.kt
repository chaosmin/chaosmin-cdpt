package tech.chaosmin.framework.utils

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import tech.chaosmin.framework.base.BaseDO
import tech.chaosmin.framework.base.BaseEntity
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.definition.SystemConst

/**
 * @author Romani min
 * @since 2021/9/10 16:20
 */
object PageQueryUtil {
    fun <E : BaseEntity<E>, D : BaseDO> queryAll(interrogator: Interrogator<E, D>, wrapper: QueryWrapper<D>): List<E> {
        val result = mutableListOf<E>()
        val page = Page<D>(0, SystemConst.DEFAULT_PAGE_SIZE)
        do {
            val data = interrogator.page(PageQuery.query(wrapper, page))
            page.current = page.current + SystemConst.DEFAULT_PAGE_SIZE
            result.addAll(data.records)
        } while (data.records.isNotEmpty() && (data.current < data.total))
        return result
    }
}