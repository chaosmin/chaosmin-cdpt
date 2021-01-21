package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.base.enums.BasicStatusEnum
import tech.chaosmin.framework.module.cdpt.domain.dao.GoodsPlanDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.GoodsPlanExt
import tech.chaosmin.framework.module.cdpt.service.GoodsPlanService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class GoodsPlanServiceImpl : ServiceImpl<GoodsPlanDAO, GoodsPlan>(), GoodsPlanService {
    override fun getByIdExt(id: Long): GoodsPlanExt? = baseMapper.getByIdExt(id)

    override fun pageExt(page: Page<GoodsPlanExt>, queryWrapper: Wrapper<GoodsPlanExt>): IPage<GoodsPlanExt> {
        return baseMapper.pageExt(page, queryWrapper)
    }

    override fun getByUser(userId: Long): List<GoodsPlan> {
        val ew = Wrappers.query<GoodsPlan>()
            .eq("user_id", userId)
            .eq("status", BasicStatusEnum.ENABLED.getCode())
        return baseMapper.selectList(ew)
    }

    override fun getByUserAndPlan(userId: Long, planId: Long): GoodsPlan? {
        val ew = Wrappers.query<GoodsPlan>()
            .eq("user_id", userId)
            .eq("product_plan_id", planId)
            .eq("status", BasicStatusEnum.ENABLED.getCode())
        return baseMapper.selectList(ew).firstOrNull()
    }

    override fun getProductIdByUser(userId: Long): List<Long> {
        val ew = Wrappers.query<GoodsPlan>()
            .select("distinct product_id")
            .eq("user_id", userId)
            .eq("status", BasicStatusEnum.ENABLED.getCode())
        return baseMapper.selectList(ew).mapNotNull { it.productId }
    }
}