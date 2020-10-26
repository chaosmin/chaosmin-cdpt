package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.RelationEnum

class IssuanceInsured(id: Long? = null) : BaseCommonDO(id) {
    var issuanceId: Long? = null
    var customerId: Long? = null
    var addressId: Long? = null
    var emailId: Long? = null
    var occupationId: Long? = null
    var phoneId: Long? = null
    var holderRelation: RelationEnum? = null
}