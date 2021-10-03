/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductRichText

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductRichTextService : IService<ProductRichText> {
    fun getByProductId(productId: Long): ProductRichText

    fun updateByProductId(productId: Long, ex: ProductRichText): ProductRichText
}