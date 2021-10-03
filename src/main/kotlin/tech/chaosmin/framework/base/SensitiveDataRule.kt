/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * SensitiveDataRule.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.base

import tech.chaosmin.framework.base.enums.SensitiveRulesEnum

/**
 * @author Romani min
 * @since 2021/9/8 17:26
 */
class SensitiveDataRule {
    /**
     * 过滤的字段名
     */
    var fieldName: String? = null

    /**
     * 脱敏规则
     */
    var format: SensitiveRulesEnum? = null
}
