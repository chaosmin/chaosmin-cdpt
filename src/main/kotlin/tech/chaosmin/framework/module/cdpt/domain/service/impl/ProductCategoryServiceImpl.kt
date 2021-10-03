/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.ProductCategoryDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductCategory
import tech.chaosmin.framework.module.cdpt.domain.service.ProductCategoryService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductCategoryServiceImpl : ServiceImpl<ProductCategoryDAO, ProductCategory>(), ProductCategoryService {
    override fun getCategoryOrCreate(name: String, subName: String): ProductCategory {
        synchronized(this) {
            val wa = Wrappers.query<ProductCategory>().eq("category_name", name).eq("category_sub_name", subName)
            var productCategory = baseMapper.selectOne(wa)
            if (productCategory == null) {
                productCategory = ProductCategory(name, subName)
                baseMapper.insert(productCategory)
            }
            return productCategory
        }
    }

    override fun getByProductIds(productIds: List<Long>): List<ProductCategory> {
        return if (productIds.isEmpty()) emptyList()
        else baseMapper.getByProductIds(productIds)
    }
}