package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.BasicStatusEnum

class CustomerAddress(id: Long? = null) : BaseCommonDO(id) {
    var customerId: Long? = null
    var status: BasicStatusEnum? = null
}