package tech.chaosmin.framework.dao.dataobject.ext

import tech.chaosmin.framework.dao.dataobject.ProductPlanLiability

/**
 * @author Romani min
 * @since 2020/12/29 21:38
 */
class ProductPlanLiabilityExt : ProductPlanLiability() {
    // 责任所属计划
    var planCode: String? = null
}