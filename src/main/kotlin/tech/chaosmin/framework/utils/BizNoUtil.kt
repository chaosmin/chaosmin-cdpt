package tech.chaosmin.framework.utils

import java.util.*

/**
 * @author Romani min
 * @since 2021/1/27 10:27
 */
object BizNoUtil {
    fun generateOrderNo(productPlanId: Long): String {
        return UUID.randomUUID().toString()
    }
}