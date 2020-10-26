package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.BasicStatusEnum

class Package(id: Long? = null) : BaseCommonDO(id) {
    var code: String? = null
    var name: String? = null
    var status: BasicStatusEnum? = null
}