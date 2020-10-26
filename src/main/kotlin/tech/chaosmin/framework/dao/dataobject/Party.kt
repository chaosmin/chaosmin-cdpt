package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.CustomerTypeEnum

class Party(id: Long? = null) : BaseCommonDO(id) {
    var type: CustomerTypeEnum? = null
}