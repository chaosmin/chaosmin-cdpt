package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductCategory

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductCategoryService : IService<ProductCategory> {
    fun getCategoryOrCreate(name: String, subName: String): ProductCategory

    fun getByProductIds(productIds: List<Long>): List<ProductCategory>
}