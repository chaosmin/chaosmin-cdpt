package tech.chaosmin.framework.module.cdpt.domain.dataobject.ext

import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlan

/**
 * @author Romani min
 * @since 2020/12/27 16:40
 */
class ProductPlanExt : ProductPlan() {
    // 保司名称
    var partnerName: String? = null

    // 产品Code
    var productCode: String? = null

    // 产品名称
    var productName: String? = null

    // 等待天数(T+N)
    var waitingDays: Int? = null
}