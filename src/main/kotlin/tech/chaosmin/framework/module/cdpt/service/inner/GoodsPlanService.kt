package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface GoodsPlanService : IService<GoodsPlan> {
    fun getByUser(userId: Long): List<GoodsPlan>

    fun getByUserAndPlan(userId: Long, planId: Long): GoodsPlan?

    fun getProductIdByUser(userId: Long): List<Long>
}