package tech.chaosmin.framework.module.cdpt.domain.dataobject.ext

import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan

/**
 * @author Romani min
 * @since 2021/1/3 19:26
 */
class GoodsPlanExt : GoodsPlan() {
    // 扩展返回字段
    /** 最大佣金比例 */
    var maxComsRatio: Double? = null

    /** 商品名称 */
    var productPlanName: String? = null

    // 扩展表
    /** 一级大类 */
    var categoryName: String? = null

    /** 二级大类 */
    var categorySubName: String? = null

    /** 产品等待期 */
    var waitingDays: Int? = null

    /** 产品代码 */
    var productCode: String? = null

    /** 产品名称 */
    var productName: String? = null

    /** 主险保额 */
    var primaryCoverage: String? = null

    /** 条款下载地址 */
    var clauseUrl: String? = null
}