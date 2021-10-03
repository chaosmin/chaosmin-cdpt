/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.ProductRichTextDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductRichText
import tech.chaosmin.framework.module.cdpt.domain.service.ProductRichTextService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductRichTextServiceImpl : ServiceImpl<ProductRichTextDAO, ProductRichText>(), ProductRichTextService {
    @Cacheable(value = ["products"], key = "#productId + ':rich-text'")
    override fun getByProductId(productId: Long): ProductRichText {
        val wa = QueryWrapper<ProductRichText>().eq("product_id", productId)
        return baseMapper.selectOne(wa)
    }

    @CachePut(value = ["products"], key = "#productId + ':rich-text'")
    override fun updateByProductId(productId: Long, ex: ProductRichText): ProductRichText {
        val wa = QueryWrapper<ProductRichText>().eq("product_id", productId)
        baseMapper.update(ex, wa)
        return baseMapper.selectOne(wa)
    }
}