package tech.chaosmin.framework.dao.dataobject

import cn.hutool.core.util.EnumUtil
import tech.chaosmin.framework.domain.enums.HttpMethodEnum

class Authority(id: Long? = null) : BaseCommonDO(id) {
    var code: String? = null
    var name: String? = null
    var url: String? = null
    var httpMethod: HttpMethodEnum? = null

    constructor(method: String?, url: String?) : this() {
        if (!method.isNullOrBlank()) {
            this.httpMethod = EnumUtil.fromStringQuietly(HttpMethodEnum::class.java, method)
        }
        this.url = url
    }
}