/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * GoodsPlanInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.domain.service.GoodsPlanService
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanLibService
import tech.chaosmin.framework.module.cdpt.domain.service.ProductPlanRaTeService
import tech.chaosmin.framework.module.cdpt.domain.service.ProductRichTextService
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsCategoryResp
import tech.chaosmin.framework.module.cdpt.logic.convert.GoodsPlanMapper
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanLibMapper
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductPlanRaTeMapper

/**
 * @author Romani min
 * @since 2021/9/4 22:19
 */
@Component
class GoodsPlanInterrogator(
    private val goodsPlanService: GoodsPlanService,
    private val productPlanLibService: ProductPlanLibService,
    private val productPlanRaTeService: ProductPlanRaTeService,
    private val productRichTextService: ProductRichTextService
) : Interrogator<GoodsPlanEntity, GoodsPlan> {
    override fun getOne(id: Long): GoodsPlanEntity {
        val goodsPlan = goodsPlanService.getById(id)
        return GoodsPlanMapper.INSTANCE.toEn(goodsPlan)?.apply {
            // 扩展信息填充
            // TODO 缓存处理
            this.liabilities = ProductPlanLibMapper.INSTANCE.toEn(productPlanLibService.listByPlanId(this.productPlanId!!))
            this.rateTable = ProductPlanRaTeMapper.INSTANCE.toEn(productPlanRaTeService.listByPlanId(this.productPlanId!!))
            this.productExternal = productRichTextService.getByProductId(this.productId!!).externalText
        } ?: GoodsPlanEntity()
    }

    override fun page(cond: PageQuery<GoodsPlan>): IPage<GoodsPlanEntity> {
        val page = goodsPlanService.page(cond.page, cond.wrapper)
        return page.convert(GoodsPlanMapper.INSTANCE::toEn)
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
        val records = page.records.filterNotNull().mapNotNull { GoodsPlanMapper.INSTANCE.toEn(it) }
        records.forEach {
            // 扩展属性
            it.liabilities = ProductPlanLibMapper.INSTANCE.toEn(productPlanLibService.listByPlanId(it.productPlanId!!))
            it.rateTable = ProductPlanRaTeMapper.INSTANCE.toEn(productPlanRaTeService.listByPlanId(it.productPlanId!!))
            it.insuranceNotice = productRichTextService.getByProductId(it.productId!!).insuranceNotice
            it.productExternal = productRichTextService.getByProductId(it.productId!!).externalText
            // stringRedisTemplate.opsForSet().add(cacheNameSpace, JsonUtil.encode(it))
        }
        goodsPlanList.addAll(records)
        // TODO 优化
        // } while (page.records.isNotEmpty() && page.pages <= currentPage)
        // return stringRedisTemplate.opsForSet().members(cacheNameSpace)?.mapNotNull {
        //     JsonUtil.decode(it, GoodsPlanEntity::class.java)
        // } ?: emptyList()
        return goodsPlanList
    }
}