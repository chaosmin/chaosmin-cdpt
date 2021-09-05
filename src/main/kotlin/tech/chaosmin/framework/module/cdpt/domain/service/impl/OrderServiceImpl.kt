/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.domain.service.impl

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.OrderDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.OrderEx
import tech.chaosmin.framework.module.cdpt.domain.service.OrderService

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
@Service
open class OrderServiceImpl : ServiceImpl<OrderDAO, Order>(), OrderService {
    override fun findByOrderNo(orderNo: String): Order {
        val ew = Wrappers.query<Order>().eq("order_no", orderNo)
        return baseMapper.selectList(ew).firstOrNull() ?: Order()
    }

    override fun pageExt(page: Page<OrderEx>, queryWrapper: Wrapper<OrderEx>): IPage<OrderEx> {
        return baseMapper.pageExt(page, queryWrapper)
    }
}