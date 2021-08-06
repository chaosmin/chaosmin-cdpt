package tech.chaosmin.framework.module.cdpt.entity.report

import tech.chaosmin.framework.base.enums.TimeTypeEnum
import java.io.Serializable
import java.util.*

/**
 * @author Romani min
 * @since 2021/8/5 15:49
 */
open class ReportEntity : Serializable {
    var timeType: TimeTypeEnum? = null

    // 统计:出单开始日期
    var startTime: Date? = null

    // 统计:出单结束日期
    var endTime: Date? = null

    // 用户ID
    var userId: Long? = null

    // 用户姓名
    var userName: String? = null
}