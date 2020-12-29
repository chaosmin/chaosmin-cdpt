package tech.chaosmin.framework.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.dao.dataobject.ProductExternal

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductExternalService : IService<ProductExternal> {
    fun selectText(productId: Long): String

    fun updateText(productId: Long, text: String?): String
}