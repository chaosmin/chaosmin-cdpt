package tech.chaosmin.framework.module.cdpt.service.inner.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.base.enums.StatusEnum
import tech.chaosmin.framework.module.cdpt.domain.dao.GoodsPlanDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.service.inner.GoodsPlanService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class GoodsPlanServiceImpl : ServiceImpl<GoodsPlanDAO, GoodsPlan>(), GoodsPlanService {
    override fun getByUser(userId: Long): List<GoodsPlan> {
        val ew = Wrappers.query<GoodsPlan>()
            .eq("user_id", userId)
            .eq("status", StatusEnum.ENABLED.getCode())
        return baseMapper.selectList(ew)
    }

    override fun getByUserAndPlan(userId: Long, planId: Long): GoodsPlan? {
        val ew = Wrappers.query<GoodsPlan>()
            .eq("user_id", userId)
            .eq("product_plan_id", planId)
            .eq("status", StatusEnum.ENABLED.getCode())
        return baseMapper.selectList(ew).firstOrNull()
    }

    override fun getProductIdByUser(userId: Long): List<Long> {
        val ew = Wrappers.query<GoodsPlan>()
            .select("distinct product_id")
            .eq("user_id", userId)
            .eq("status", StatusEnum.ENABLED.getCode())
        return baseMapper.selectList(ew).mapNotNull { it.productId }
    }
}