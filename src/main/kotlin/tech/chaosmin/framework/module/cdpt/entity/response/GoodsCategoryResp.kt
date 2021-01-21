package tech.chaosmin.framework.module.cdpt.entity.response

import java.io.Serializable

/**
 * @author Romani min
 * @since 2021/1/12 22:12
 */
class GoodsCategoryResp() : Serializable {
    var id: String? = null
    var name: String? = null
    var children: List<GoodsCategoryResp>? = null

    constructor(id: String, name: String) : this() {
        this.id = id
        this.name = name
    }
}