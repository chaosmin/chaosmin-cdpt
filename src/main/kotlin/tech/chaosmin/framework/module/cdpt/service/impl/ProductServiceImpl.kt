package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.ProductDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Product
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductExt
import tech.chaosmin.framework.module.cdpt.service.ProductService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductServiceImpl : ServiceImpl<ProductDAO, Product>(), ProductService {
    override fun listEqProductCode(code: String): List<Product> {
        val wa = QueryWrapper<Product>().eq("product_code", code)
        return baseMapper.selectList(wa)
    }

    override fun pageExt(page: Page<ProductExt>, queryWrapper: Wrapper<ProductExt>): IPage<ProductExt> {
        return baseMapper.pageExt(page, queryWrapper)
    }

    override fun setCategories(productId: Long, categoryIds: List<Long>) {
        baseMapper.removeRelations(productId)
        baseMapper.addRelations(productId, categoryIds)
    }
}