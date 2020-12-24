package tech.chaosmin.framework.domain.entity

import tech.chaosmin.framework.domain.entity.base.BaseEntity
import tech.chaosmin.framework.domain.enums.BasicStatusEnum

/**
 * @author Romani min
 * @since 2020/12/23 21:35
 */
class ProductEntity(id: Long? = null) : BaseEntity(id) {
    var partnerId: Long? = null
    var productCode: String? = null
    var productName: String? = null
    var productSubName: String? = null
    var partnerProductNo: String? = null
    var productDesc: String? = null
    var status: BasicStatusEnum? = null
}