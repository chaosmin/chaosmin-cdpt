/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * TwoLatitude.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.entity.condition

import java.io.Serializable
import java.util.*

/**
 * @author Romani min
 * @since 2021/9/10 15:47
 */
class TwoLatitude<F, S>() : Serializable {
    var first: List<F>? = null
    var second: List<S>? = null

    constructor(first: F?, second: S?) : this() {
        if (first != null) this.first = Collections.singletonList(first)
        if (second != null) this.second = Collections.singletonList(second)
    }
}