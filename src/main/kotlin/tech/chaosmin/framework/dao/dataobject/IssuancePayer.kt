package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.RelationEnum

class IssuancePayer(id: Long? = null) : BaseCommonDO(id) {
    var issuanceId: Long? = null
    var insuredRelation: RelationEnum? = null
    var holderRelation: RelationEnum? = null
}