package tech.chaosmin.framework.module.cdpt.domain.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.OrderTempDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.OrderDraft
import tech.chaosmin.framework.module.cdpt.domain.service.OrderDraftService

/**
 * @author Romani min
 * @since 2021/6/27 20:05
 */
@Service
open class OrderDraftServiceImpl : ServiceImpl<OrderTempDAO, OrderDraft>(), OrderDraftService {
    override fun listByOrderNo(orderNo: String): List<OrderDraft> {
        val ew = Wrappers.query<OrderDraft>().eq("order_no", orderNo)
        return baseMapper.selectList(ew)
    }

    override fun saveOrUpdate(orderNo: String, param: String) {
        val list = listByOrderNo(orderNo)
        if (list.isEmpty()) baseMapper.insert(OrderDraft().apply {
            this.orderNo = orderNo
            this.param = param
        })
        else list.forEach { baseMapper.updateById(it.apply { this.param = param }) }
    }
}