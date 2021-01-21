package tech.chaosmin.framework.module.cdpt.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.GoodsPlanExt
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsCategoryResp

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface GoodsPlanService : IService<GoodsPlan> {
    fun getByIdExt(id: Long): GoodsPlanExt?

    fun pageExt(page: Page<GoodsPlanExt>, queryWrapper: Wrapper<GoodsPlanExt>): IPage<GoodsPlanExt>

    fun getByUser(userId: Long): List<GoodsPlan>

    fun getByUserAndPlan(userId: Long, planId: Long): GoodsPlan?

    fun getProductIdByUser(userId: Long): List<Long>
}