/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Product
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductEx

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductService : IService<Product> {
    fun listEqProductCode(code: String): List<Product>

    fun getByIdExt(id: Long): ProductEx?

    fun pageExt(page: Page<ProductEx>, queryWrapper: Wrapper<ProductEx>): IPage<ProductEx>

    fun setCategories(productId: Long, categoryIds: List<Long>)
}