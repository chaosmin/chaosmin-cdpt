/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PaymentNotifyEntiity.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.payment.entity

import tech.chaosmin.framework.module.cdpt.entity.enums.PayStatusEnum

/**
 * @author Romani min
 * @since 2021/9/5 21:12
 */
class PaymentNotifyEntity() {
    var orderNo: String? = null
    var status: PayStatusEnum? = null

    constructor(orderNo: String, status: PayStatusEnum) : this() {
        this.orderNo = orderNo
        this.status = status
    }
}