/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * User.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.report.entity.condition

/**
 * @author Romani min
 * @since 2021/9/10 15:40
 */
open class UserLatitude : DepartmentLatitude() {
    var userId: Long? = null
    var userName: String? = null
}