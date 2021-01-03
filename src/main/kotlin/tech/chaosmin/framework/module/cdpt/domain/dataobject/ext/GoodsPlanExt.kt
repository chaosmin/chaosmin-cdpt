package tech.chaosmin.framework.module.cdpt.domain.dataobject.ext

import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan

/**
 * @author Romani min
 * @since 2021/1/3 19:26
 */
class GoodsPlanExt : GoodsPlan() {
    // 一级大类
    var categoryName: String? = null

    // 二级大类
    var categorySubName: String? = null

    // 产品代码
    var productCode: String? = null

    // 商品名称
    var goodsName: String? = null

    // 最大佣金比例
    var maxComsRatio: Double? = null
}