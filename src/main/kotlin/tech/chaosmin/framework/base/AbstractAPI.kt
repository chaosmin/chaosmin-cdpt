/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * AbstractShareApi.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.base

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2021/9/4 22:51
 */
abstract class AbstractAPI<D : BaseDO, E : BaseEntity<E>, RE : BaseReq, RQ : BaseResp>(
    private val convert: IConvert<E, RE, RQ>,
    private val interrogator: Interrogator<E, D>,
    private val handler: AbstractTemplateOperate<E, E>
) : BaseAPI<RE, RQ> {
    // 提供覆写基础过滤条件的方法
    open fun baseFilterCondition(condition: PageQuery<D>): PageQuery<D> {
        return condition
    }

    override fun selectById(@PathVariable("id") id: Long): RestResult<RQ> {
        val result = interrogator.getOne(id)
        return if (result == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(convert.toResp(result))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<RQ>> {
        val condition = RequestUtil.getQueryCondition<D>(request)
        val page = interrogator.page(baseFilterCondition(condition))
        return RestResultExt.successRestResult(page.convert { convert.toResp(it) })
    }

    override fun save(@RequestBody req: RE): RestResult<RQ> {
        val entity = convert.toEn(req).save()
        val restResult = handler.operate(entity)
        return restResult.convert { convert.toResp(it) }
    }

    override fun update(@PathVariable("id") id: Long, @RequestBody req: RE): RestResult<RQ> {
        val entity = convert.toEn(req).update(id)
        val restResult = handler.operate(entity)
        return restResult.convert { convert.toResp(it) }

    }

    override fun delete(@PathVariable("id") id: Long): RestResult<RQ> {
        val entity = Class<E>::newInstance.call().remove(id)
        val restResult = handler.operate(entity)
        return restResult.convert { convert.toResp(it) }
    }
}