package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ProductDAO
import tech.chaosmin.framework.dao.dataobject.Product
import tech.chaosmin.framework.service.ProductService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductServiceImpl : ServiceImpl<ProductDAO, Product>(), ProductService