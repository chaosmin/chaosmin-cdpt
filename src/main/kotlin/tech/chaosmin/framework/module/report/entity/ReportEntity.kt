package tech.chaosmin.framework.module.report.entity

import tech.chaosmin.framework.module.report.entity.enums.ReportTypeEnum
import java.io.Serializable

/**
 * @author Romani min
 * @since 2021/8/5 15:49
 */
open class ReportEntity<T, R>(val type: ReportTypeEnum) : Serializable {
    // 报表生成条件
    var condition: List<T>? = null

    // 报表内容
    var result: R? = null
}