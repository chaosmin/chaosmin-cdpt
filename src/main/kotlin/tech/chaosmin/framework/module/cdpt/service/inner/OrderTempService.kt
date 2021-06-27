package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.OrderTemp

/**
 * @author Romani min
 * @since 2021/6/27 20:05
 */
interface OrderTempService : IService<OrderTemp> {
    fun listByOrderNo(orderNo: String): List<OrderTemp>
    fun saveOrUpdate(orderNo: String, param: String)
}