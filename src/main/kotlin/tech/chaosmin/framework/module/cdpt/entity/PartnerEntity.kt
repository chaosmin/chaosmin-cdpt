package tech.chaosmin.framework.module.cdpt.entity

import tech.chaosmin.framework.base.BaseEntity

/**
 * 保险公司信息实体对象 <p>
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class PartnerEntity(id: Long? = null) : BaseEntity<PartnerEntity>(id) {
    var partnerCode: String? = null
    var partnerName: String? = null
    var publicKey: String? = null
    var securityKey: String? = null
}