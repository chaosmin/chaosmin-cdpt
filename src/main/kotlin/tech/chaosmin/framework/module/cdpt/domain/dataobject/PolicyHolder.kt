package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/26 10:39
 */
class PolicyHolder(id: Long? = null) : BaseDO(id, 0) {
    /** 保单ID */
    var policyId: Long? = null

    /** 与主被保人关系v */
    var mainInsuredRelation: Int? = null

    /** 实体类型 1-个人客户 2-公司客户 */
    var partyType: Int? = null

    /** 姓名 */
    var name: String? = null

    /** 证件类型 */
    var certiType: Int? = null

    /** 证件号码 */
    var certiNo: String? = null

    /** 生日 */
    var birthday: Date? = null

    /** 性别 */
    var gender: Int? = null

    /** 电话号码 */
    var phoneNo: String? = null
}