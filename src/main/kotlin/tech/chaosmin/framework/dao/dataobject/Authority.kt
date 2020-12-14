package tech.chaosmin.framework.dao.dataobject

import cn.hutool.core.util.EnumUtil
import tech.chaosmin.framework.domain.enums.HttpMethodEnum

class Authority(id: Long? = null) : BaseCommonDO(id, 0) {
    // 权限编码
    var code: String? = null

    // 权限名称
    var name: String? = null

    // 资源URL
    var url: String? = null

    // 资源权限
    var httpMethod: HttpMethodEnum? = null

    constructor(method: String?, url: String?) : this() {
        if (!method.isNullOrBlank()) {
            this.httpMethod = EnumUtil.fromStringQuietly(HttpMethodEnum::class.java, method)
        }
        this.url = url
    }
}