/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * TwoLatitudeReq.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.entity.request

import java.io.Serializable

/**
 * @author Romani min
 * @since 2021/9/13 12:57
 */
class TwoLatitudeReq<F, S> : Serializable {
    var first: F? = null
    var second: S? = null
}