package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO

/**
 * 产品特约&须知
 * @author Romani min
 * @since 2020/12/23 11:06
 */
class ProductExternal(id: Long? = null) : BaseDO(id, 0) {
    // 产品ID
    var productId: Long? = null

    // 投保提示
    var insuranceNotice: String? = null

    // 产品扩展文本
    var externalText: String? = null

    constructor(notice: String?, text: String?) : this() {
        this.insuranceNotice = notice
        this.externalText = text
    }
}