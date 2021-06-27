package tech.chaosmin.framework.module.cdpt.service.inner.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.OrderTempDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.OrderTemp
import tech.chaosmin.framework.module.cdpt.service.inner.OrderTempService

/**
 * @author Romani min
 * @since 2021/6/27 20:05
 */
@Service
open class OrderTempServiceImpl : ServiceImpl<OrderTempDAO, OrderTemp>(), OrderTempService {
    override fun listByOrderNo(orderNo: String): List<OrderTemp> {
        val ew = Wrappers.query<OrderTemp>().eq("order_no", orderNo)
        return baseMapper.selectList(ew)
    }

    override fun saveOrUpdate(orderNo: String, param: String) {
        val list = listByOrderNo(orderNo)
        if (list.isEmpty()) baseMapper.insert(OrderTemp().apply {
            this.orderNo = orderNo
            this.param = param
        })
        else list.forEach { baseMapper.updateById(it.apply { this.param = param }) }
    }
}