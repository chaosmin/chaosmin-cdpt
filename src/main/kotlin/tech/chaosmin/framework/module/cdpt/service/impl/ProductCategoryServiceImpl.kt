package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.ProductCategoryDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductCategory
import tech.chaosmin.framework.module.cdpt.service.ProductCategoryService

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
}