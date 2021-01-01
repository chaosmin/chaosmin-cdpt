package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class PartnerEntity(id: Long? = null) : BaseEntity(id) {
    var partnerCode: String? = null
    var partnerName: String? = null
    var publicKey: String? = null
}