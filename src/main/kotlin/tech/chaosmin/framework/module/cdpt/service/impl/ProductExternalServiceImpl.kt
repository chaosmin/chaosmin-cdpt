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
    override fun selectText(productId: Long): String {
        val wa = QueryWrapper<ProductExternal>().eq("product_id", productId)
        val agreement = baseMapper.selectOne(wa)
        return agreement.externalText ?: ""
    }

    @CachePut(value = ["product-external"], key = "#productId")
    override fun updateText(productId: Long, text: String?): String {
        val wa = QueryWrapper<ProductExternal>().eq("product_id", productId)
        val bean = ProductExternal()
        bean.externalText = text ?: ""
        baseMapper.update(bean, wa)
        return text ?: ""
    }
}