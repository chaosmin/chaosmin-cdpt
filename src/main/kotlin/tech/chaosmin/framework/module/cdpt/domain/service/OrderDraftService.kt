package tech.chaosmin.framework.module.cdpt.domain.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.OrderDraft

/**
 * @author Romani min
 * @since 2021/6/27 20:05
 */
interface OrderDraftService : IService<OrderDraft> {
    fun listByOrderNo(orderNo: String): List<OrderDraft>
    fun saveOrUpdate(orderNo: String, param: String)
}