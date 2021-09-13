/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * DashboardHandler.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.logic.handler

import cn.hutool.core.date.DateUtil
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyEx
import tech.chaosmin.framework.module.cdpt.entity.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.logic.interrogator.PolicyInterrogator
import tech.chaosmin.framework.module.report.entity.response.DashboardResp
import tech.chaosmin.framework.utils.PageQueryUtil
import java.util.*

/**
 * @author Romani min
 * @since 2021/9/13 13:37
 */
@Component
class DashboardHandler(private val policyInterrogator: PolicyInterrogator) : AbstractTemplateOperate<Date, DashboardResp>() {
    override fun processor(arg: Date, res: RestResult<DashboardResp>): RestResult<DashboardResp> {
        val startOfThisWeek = DateUtil.beginOfWeek(Date()).time
        val ew = Wrappers.query<PolicyEx>().eq("policy.status", PolicyStatusEnum.INSURED.getCode())
        // 全量数据
        // TODO 优化在大数据量情况下的表现
        val list = PageQueryUtil.queryAll(policyInterrogator, ew)
        val lineData = list.groupBy { DateUtil.beginOfDay(it.createTime).time }.filter { it.key >= startOfThisWeek }
        val dashboardResp = DashboardResp().apply {
            this.policies.apply {
                value = list.size.toDouble()
                actualData = (0..6).mapNotNull { lineData[startOfThisWeek + it * 24 * 60 * 60 * 1000]?.size?.toDouble() ?: 0.0 }
            }
            this.insureds.apply {
                value = list.sumByDouble { it.insuredSize?.toDouble() ?: 0.0 }
                actualData = (0..6).mapNotNull {
                    lineData[startOfThisWeek + it * 24 * 60 * 60 * 1000]?.sumByDouble { i -> i.insuredSize?.toDouble() ?: 0.0 } ?: 0.0
                }
            }
            this.premium.apply {
                value = list.sumByDouble { it.actualPremium ?: 0.0 }
                actualData =
                    (0..6).mapNotNull { lineData[startOfThisWeek + it * 24 * 60 * 60 * 1000]?.sumByDouble { i -> i.actualPremium ?: 0.0 } ?: 0.0 }
            }
            this.issuers.value = list.distinctBy { it.creator }.size.toDouble()
        }
        return RestResultExt.successRestResult(dashboardResp)
    }
}