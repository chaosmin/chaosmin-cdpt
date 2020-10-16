package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.HttpMethodEnum

class Authority(id: Long? = null) : BaseCommonDO(id, 0) {
    var code: String? = null
    var name: String? = null
    var url: String? = null
    var httpMethod: HttpMethodEnum? = null
}