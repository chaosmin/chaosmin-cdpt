package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.BasicStatusEnum

class PersonEmail(id: Long? = null) : BaseCommonDO(id) {
    var personId: Long? = null
    var status: BasicStatusEnum? = null
}