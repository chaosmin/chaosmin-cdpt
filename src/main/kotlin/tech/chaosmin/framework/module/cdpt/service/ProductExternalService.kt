package tech.chaosmin.framework.module.cdpt.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductExternal

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductExternalService : IService<ProductExternal> {
    fun selectText(productId: Long): String

    fun updateText(productId: Long, text: String?): String
}