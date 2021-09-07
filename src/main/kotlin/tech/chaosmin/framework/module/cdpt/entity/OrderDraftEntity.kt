/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * OrderDraftEntity.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * @author Romani min
 * @since 2021/9/6 17:10
 */
class OrderDraftEntity(id: Long? = null) : BaseEntity<OrderDraftEntity>(id) {
    var orderNo: String? = null
    var param: String? = null
}