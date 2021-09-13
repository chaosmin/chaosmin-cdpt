/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PolicyLatitude.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.entity.condition

import tech.chaosmin.framework.module.cdpt.entity.enums.PayStatusEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum
import tech.chaosmin.framework.module.cdpt.entity.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.report.entity.enums.TimeTypeEnum

/**
 * @author Romani min
 * @since 2021/9/10 15:41
 */
open class PolicyLatitude : TimeLatitude() {
    var timeType: TimeTypeEnum? = null
    var status: PolicyStatusEnum? = null
    var payStatus: PayStatusEnum? = null
    var payType: PayTypeEnum? = null
}