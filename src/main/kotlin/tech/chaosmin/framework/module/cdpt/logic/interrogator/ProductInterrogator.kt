/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * ProductInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductEx
import tech.chaosmin.framework.module.cdpt.domain.service.ProductService
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.ProductMapper

/**
 * @author Romani min
 * @since 2021/9/4 22:24
 */
@Component
class ProductInterrogator(private val productService: ProductService) : Interrogator<ProductEntity, ProductEx> {
    override fun getOne(id: Long): ProductEntity? {
        val productEx = productService.getByIdExt(id)
        return ProductMapper.INSTANCE.exToEn(productEx)
    }

    override fun page(cond: PageQuery<ProductEx>): IPage<ProductEntity> {
        val page = productService.pageExt(cond.page, cond.wrapper)
        val result = page.convert {
            ProductMapper.INSTANCE.exToEn(it)!!.apply {
                // TODO 产品的附加信息
//                val ex = productExternalService.getByProductId(it.id!!)
//                this.insuranceNotice = ex.insuranceNotice
//                this.externalText = ex.externalText
            }
        }
        return result
    }
}