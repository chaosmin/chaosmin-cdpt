package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.GoodsPlanExt
import tech.chaosmin.framework.module.cdpt.entity.GoodsCategoryEntity
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.GoodsPlanMapper
import tech.chaosmin.framework.module.cdpt.service.GoodsPlanService
import tech.chaosmin.framework.module.cdpt.service.ProductCategoryService
import tech.chaosmin.framework.utils.JsonUtil

/**
 * 可售保险产品数据查询逻辑 <p>
 * <p>
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class GoodsPlanQueryLogic(
    private val goodsPlanService: GoodsPlanService,
    private val productCategoryService: ProductCategoryService
) : BaseQueryLogic<GoodsPlanEntity, GoodsPlanExt> {
    private val logger = LoggerFactory.getLogger(GoodsPlanQueryLogic::class.java)

    override fun get(id: Long): GoodsPlanEntity? {
        val goodsPlan = goodsPlanService.getByIdExt(id)
        logger.debug("GetById($id) => ${JsonUtil.encode(goodsPlan)}")
        val goodsPlanEntity = GoodsPlanMapper.INSTANCE.convertEx2Entity(goodsPlan)
        logger.debug("Convert2Entity => ${JsonUtil.encode(goodsPlanEntity)}")
        return goodsPlanEntity
    }

    override fun page(cond: PageQuery<GoodsPlanExt>): IPage<GoodsPlanEntity?> {
        val page = goodsPlanService.pageExt(cond.page, cond.wrapper)
        logger.debug("Page(${JsonUtil.encode(cond)}) => ${JsonUtil.encode(page)}")
        val result = page.convert(GoodsPlanMapper.INSTANCE::convertEx2Entity)
        logger.debug("Convert2Entity => ${JsonUtil.encode(result)}")
        return result
    }

    fun getGoodsCategories(userId: Long): List<GoodsCategoryEntity> {
        val productIds = goodsPlanService.getProductIdByUser(userId)
        val categories = productCategoryService.getByProductIds(productIds)
        return categories.map { GoodsCategoryEntity(it) }
    }
}