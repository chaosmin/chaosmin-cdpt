package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductExternal

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ProductExternalService : IService<ProductExternal> {
    fun getByProductId(productId: Long): ProductExternal

    fun updateByProductId(productId: Long, ex: ProductExternal): ProductExternal
}