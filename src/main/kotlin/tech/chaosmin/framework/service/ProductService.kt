package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.dao.dataobject.Product
import tech.chaosmin.framework.dao.dataobject.ext.ProductExt

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductService : IService<Product> {
    fun pageExt(page: Page<ProductExt>, queryWrapper: Wrapper<ProductExt>): IPage<ProductExt>
    fun setCategories(productId: Long, categoryIds: List<Long>)
}