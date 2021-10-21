package tech.chaosmin.framework.module.mgmt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO

/**
 * @author Romani min
 * @since 2021/2/18 16:48
 */
open class LetterHead(id: Long? = null) : BaseDO(id, 0) {
    // 用户ID
    var userId: Long? = null

    // 抬头名称
    var title: String? = null

    // 抬头证件号
    var certiNo: String? = null

    // 抬头状态
    var status: Int? = null
}