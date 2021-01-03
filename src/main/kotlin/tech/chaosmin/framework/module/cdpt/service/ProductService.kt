package tech.chaosmin.framework.module.cdpt.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Product
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductExt

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductService : IService<Product> {
    fun listEqProductCode(code: String): List<Product>

    fun getByIdExt(id: Long): ProductExt?

    fun pageExt(page: Page<ProductExt>, queryWrapper: Wrapper<ProductExt>): IPage<ProductExt>

    fun setCategories(productId: Long, categoryIds: List<Long>)
}