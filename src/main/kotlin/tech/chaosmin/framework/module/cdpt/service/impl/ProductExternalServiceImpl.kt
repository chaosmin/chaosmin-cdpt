package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.ProductExternalDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductExternal
import tech.chaosmin.framework.module.cdpt.service.ProductExternalService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductExternalServiceImpl : ServiceImpl<ProductExternalDAO, ProductExternal>(), ProductExternalService {
    @Cacheable(value = ["product-external"], key = "#productId")
    override fun getByProductId(productId: Long): ProductExternal {
        val wa = QueryWrapper<ProductExternal>().eq("product_id", productId)
        return baseMapper.selectOne(wa)
    }

    @CachePut(value = ["product-external"], key = "#productId")
    override fun updateByProductId(productId: Long, ex: ProductExternal): ProductExternal {
        val wa = QueryWrapper<ProductExternal>().eq("product_id", productId)
        baseMapper.update(ex, wa)
        return baseMapper.selectOne(wa)
    }
}