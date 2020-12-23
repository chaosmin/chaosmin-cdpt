package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ProductCategoryDAO
import tech.chaosmin.framework.dao.dataobject.ProductCategory
import tech.chaosmin.framework.service.ProductCategoryService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductCategoryServiceImpl : ServiceImpl<ProductCategoryDAO, ProductCategory>(), ProductCategoryService