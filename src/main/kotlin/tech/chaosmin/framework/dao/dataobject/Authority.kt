package tech.chaosmin.framework.dao.dataobject

import cn.hutool.core.util.EnumUtil
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import tech.chaosmin.framework.domain.enums.HttpMethodEnum

class Authority(id: Long? = null) : BaseCommonDO(id, 0), GrantedAuthority {
    // 父权限ID
    var parentId: Long? = null

    // 类型
    var type: Int? = null

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

    @JsonIgnore
    override fun getAuthority(): String = "$httpMethod $url"
}