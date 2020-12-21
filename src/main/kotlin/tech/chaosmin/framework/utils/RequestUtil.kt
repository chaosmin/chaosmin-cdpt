package tech.chaosmin.framework.utils

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import tech.chaosmin.framework.domain.PageQuery
import tech.chaosmin.framework.utils.StringUtil.upperCamel
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * 前端查询条件解析工具 <br/>
 * <br/>
 * 语法如下: <br/>
 * 分页查询: P_NUM=1 <br/>
 * 分页大小: P_SIZE=10 <br/>
 * 查询顺序: O_field=1 顺序查询 O_filed=0 逆序查询, 如 O_name=1 <br/>
 * equals查询: EQ_filed=`value`, 如EQ_name=romani <br/>
 * not equals查询: NEQ_field=`value`, 如NEQ_name=romani <br/>
 * 右匹配like查询: LIKE_field=`value`, 如LIKE_name=romani <br/>
 * 模糊匹配like查询: ALIKE_field=`value`, 如ALIKE_name=romani <br/>
 * 大于查询: GT_field=`value`, 如GT_sum=10 <br/>
 * 大于等于查询: GE_field=`value`, 如GE_sum=10 <br/>
 * 小于查询: LT_field=`value`, 如LT_sum=10 <br/>
 * 小于等于查询: LE_field=`value`, 如LE_sum=10 <br/>
 * in查询: IN_field=`value`, 如IN_name=romani,akiman <br/>
 * not in查询: NIN_field=`value`, 如NIN_name=romani,akiman <br/>
 * 区间查询: BETWEEN_field=`value`, 如BETWEEN_time=2020-01-01,2020-02-01 <br/>
 * <br/>
 * 其中field命名格式为驼峰式, 当转换为查询sql时会根据驼峰命名自动转换为下划线格式 <br/>
 * 如`EQ_userName=romani&GE_age=10`转换为`user_name=romani and age>=10`
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

    /**
     * 获取分页/查询条件
     * E 转换后的response DTO type
     * T 查询的base bean type
     *
     * @param request HTTP请求体
     * @return [PageQuery]
     */
    fun <T> getQueryCondition(request: HttpServletRequest): PageQuery<T> {
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
                    if (values[1].toUpperCase() == "NUM") page.current = value.toLong()
                    else if (values[1].toUpperCase() == "SIZE") page.size = value.toLong()
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
        return PageQuery(page, wrapper)
    }
}