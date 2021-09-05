package tech.chaosmin.framework.base

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_PAGE_SIZE

class PageQuery<T>(var page: Page<T>, var wrapper: QueryWrapper<T>) {
    companion object {
        fun <T> inQuery(field: String, value: List<Any>?, page: Page<T> = Page(0, DEFAULT_PAGE_SIZE)): PageQuery<T> {
            return query(Wrappers.query<T>().`in`(field, value), page)
        }

        fun <T> eqOne(field: String, value: Any?, page: Page<T> = Page(0, 1)): PageQuery<T> {
            return query(Wrappers.query<T>().eq(field, value), page)
        }

        fun <T> eqQuery(field: String, value: Any?, page: Page<T> = Page(0, DEFAULT_PAGE_SIZE)): PageQuery<T> {
            return query(Wrappers.query<T>().eq(field, value), page)
        }

        fun <T> query(wrapper: QueryWrapper<T>, page: Page<T> = Page(0, DEFAULT_PAGE_SIZE)): PageQuery<T> {
            return PageQuery(page, wrapper)
        }

        fun <T> emptyQuery(page: Page<T> = Page(0, DEFAULT_PAGE_SIZE)): PageQuery<T> {
            return PageQuery(page, Wrappers.query())
        }
    }
}