package tech.chaosmin.framework.web.utils

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import tech.chaosmin.framework.domain.PageQueryDTO
import tech.chaosmin.framework.utils.StringUtil.upperCamel
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * 前端查询条件解析工具 <br/>
 * 分页查询: P_NUM=1
 * 分页大小: P_SIZE=10
 * 查询顺序: O_field=1 顺序查询
 */
object RequestUtil {
    private const val SPLIT_CHAR = "_"
    private const val OPERATE_PAGE = "P"
    private const val OPERATE_ORDER = "O"
    private const val OPERATE_EQ = "EQ"
    private const val OPERATE_NEQ = "NEQ"
    private const val OPERATE_LIKE = "LIKE"
    private const val OPERATE_ALIKE = "ALIKE"
    private const val OPERATE_GT = "GT"
    private const val OPERATE_GE = "GE"
    private const val OPERATE_LT = "LT"
    private const val OPERATE_LE = "LE"
    private const val OPERATE_IN = "IN"
    private const val OPERATE_NIN = "NIN"
    private const val OPERATE_BETWEEN = "BETWEEN"

    fun <T> getQueryCondition(request: HttpServletRequest): PageQueryDTO<T> {
        val parameterMap = request.parameterMap
        val page = Page<T>(0, 10)
        val wrapper = QueryWrapper<T>()
        parameterMap.filter { (_, v) -> v.isNotEmpty() }.filter { (_, v) -> v[0].isNotBlank() }.forEach { (k, v) ->
            val values = k.split(SPLIT_CHAR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val operation = values[0]
            val value = v[0]
            val field = StringJoiner(".")
            values.drop(1).forEach { field.add("""`${it.upperCamel()}`""") }
            when (operation.toUpperCase()) {
                OPERATE_PAGE -> {
                    if (values[1] == "NUM") page.current = value.toLong()
                    else if (values[1] == "SIZE") page.size = value.toLong()
                }
                OPERATE_ORDER -> wrapper.orderBy(true, value == "1", field.toString())
                OPERATE_EQ -> wrapper.eq(field.toString(), value)
                OPERATE_NEQ -> wrapper.ne(field.toString(), value)
                OPERATE_LIKE -> wrapper.likeRight(field.toString(), value)
                OPERATE_ALIKE -> wrapper.like(field.toString(), value)
                OPERATE_GT -> wrapper.gt(field.toString(), value)
                OPERATE_GE -> wrapper.ge(field.toString(), value)
                OPERATE_LT -> wrapper.lt(field.toString(), value)
                OPERATE_LE -> wrapper.le(field.toString(), value)
                OPERATE_IN -> wrapper.inSql(field.toString(), value)
                OPERATE_NIN -> wrapper.notInSql(field.toString(), value)
                OPERATE_BETWEEN -> {
                    val strings = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    if (strings.size == 2) {
                        wrapper.between(field.toString(), strings[0], strings[1])
                    }
                }
            }
        }
        return PageQueryDTO(page, wrapper)
    }
}