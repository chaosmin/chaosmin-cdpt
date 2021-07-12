package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsCategoryResp
import tech.chaosmin.framework.module.cdpt.helper.mapper.GoodsPlanMapper
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanLiabilityMapper
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanRateTableMapper
import tech.chaosmin.framework.module.cdpt.service.inner.GoodsPlanService
import tech.chaosmin.framework.module.cdpt.service.inner.PlanLiabilityService
import tech.chaosmin.framework.module.cdpt.service.inner.PlanRateTableService
import tech.chaosmin.framework.module.cdpt.service.inner.ProductExternalService

/**
 * 可售保险产品数据查询逻辑 <p>
 * <p>
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class GoodsPlanQueryLogic(
    private val goodsPlanService: GoodsPlanService,
    private val planLiabilityService: PlanLiabilityService,
    private val planRateTableService: PlanRateTableService,
    private val productExternalService: ProductExternalService
) : BaseQueryLogic<GoodsPlanEntity, GoodsPlan> {

    override fun get(id: Long): GoodsPlanEntity? {
        val goodsPlan = goodsPlanService.getById(id)
        return GoodsPlanMapper.INSTANCE.convert2Entity(goodsPlan)?.apply {
            liabilities = PlanLiabilityMapper.INSTANCE.convert2Entity(planLiabilityService.listByPlanId(this.productPlanId!!))
            rateTable = PlanRateTableMapper.INSTANCE.convert2Entity(planRateTableService.listByPlanId(this.productPlanId!!))
            productExternal = productExternalService.getByProductId(this.productId!!).externalText
        }
    }

    override fun page(cond: PageQuery<GoodsPlan>): IPage<GoodsPlanEntity> {
        val page = goodsPlanService.page(cond.page, cond.wrapper)
        return page.convert(GoodsPlanMapper.INSTANCE::convert2Entity)
    }

    fun searchCategories(userId: Long): List<GoodsCategoryResp> {
        val list = searchGoodsPlan(userId, PageQuery.emptyQuery()).map { it.categoryName to it.categorySubName }
        return list.groupBy { it.first }.map { (key, value) ->
            GoodsCategoryResp(key!!, key).apply {
                this.children = value.mapNotNull { it.second }.distinct().map { GoodsCategoryResp(it, it) }
            }
        }
    }

    fun searchGoodsPlan(userId: Long, cond: PageQuery<GoodsPlan>): List<GoodsPlanEntity> {
        val goodsPlanList = mutableListOf<GoodsPlanEntity>()
        // val cacheNameSpace = "goods-plan:$userId"
        var currentPage = 0L
        val ew = cond.wrapper.eq("user_id", userId)
        // do {
        val page = goodsPlanService.page(Page(currentPage, 1000), ew)
        currentPage += 1
        val records = page.records.filterNotNull().mapNotNull { GoodsPlanMapper.INSTANCE.convert2Entity(it) }
        records.forEach {
            // 扩展属性
            it.liabilities = PlanLiabilityMapper.INSTANCE.convert2Entity(planLiabilityService.listByPlanId(it.productPlanId!!))
            it.rateTable = PlanRateTableMapper.INSTANCE.convert2Entity(planRateTableService.listByPlanId(it.productPlanId!!))
            it.insuranceNotice = productExternalService.getByProductId(it.productId!!).insuranceNotice
            it.productExternal = productExternalService.getByProductId(it.productId!!).externalText
            // stringRedisTemplate.opsForSet().add(cacheNameSpace, JsonUtil.encode(it))
        }
        goodsPlanList.addAll(records)
        // } while (page.records.isNotEmpty() && page.pages <= currentPage)
        // return stringRedisTemplate.opsForSet().members(cacheNameSpace)?.mapNotNull {
        //     JsonUtil.decode(it, GoodsPlanEntity::class.java)
        // } ?: emptyList()
        return goodsPlanList
    }
}