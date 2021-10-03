/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * GoodsPlanShareProvider.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.AbstractAPI
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.module.cdpt.api.GoodsPlanAPI
import tech.chaosmin.framework.module.cdpt.api.convert.GoodsPlanConvert
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.request.GoodsPlanReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsCategoryResp
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsPlanResp
import tech.chaosmin.framework.module.cdpt.logic.handler.GoodsPlanModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.interrogator.GoodsPlanInterrogator
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.utils.SecurityUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class GoodsPlanProvider(
    private val goodsPlanInterrogator: GoodsPlanInterrogator,
    private val goodsPlanModifyHandler: GoodsPlanModifyHandler
) : AbstractAPI<GoodsPlan, GoodsPlanEntity, GoodsPlanReq, GoodsPlanResp>(
    GoodsPlanConvert.INSTANCE, goodsPlanInterrogator, goodsPlanModifyHandler
), GoodsPlanAPI {

    override fun user(userId: Long, request: HttpServletRequest): RestResult<List<GoodsPlanResp>> {
        val queryCondition = RequestUtil.getQueryCondition<GoodsPlan>(request)
        queryCondition.wrapper.eq("goods_plan.status", StatusEnum.ENABLED.getCode())
        val list = goodsPlanInterrogator.searchGoodsPlan(userId, queryCondition)
        return RestResultExt.successRestResult(GoodsPlanConvert.INSTANCE.toResp(list))
    }

    override fun userCategories(userId: Long): RestResult<List<GoodsCategoryResp>> {
        return RestResultExt.successRestResult(goodsPlanInterrogator.searchCategories(userId))
    }

    override fun baseFilterCondition(condition: PageQuery<GoodsPlan>): PageQuery<GoodsPlan> {
        condition.wrapper.eq("goods_plan.status", StatusEnum.ENABLED.getCode())
        if (!SecurityUtil.getUserDetails().isAdmin) {
            // 如果是非管理员用户, 分页(列表)查询已授权产品时, 只能查自己授权出去的产品
            condition.wrapper.eq("authorizer_id", SecurityUtil.getUserId())
        }
        return condition
    }
}