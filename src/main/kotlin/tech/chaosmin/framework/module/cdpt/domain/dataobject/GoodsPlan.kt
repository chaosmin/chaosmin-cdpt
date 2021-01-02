package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 11:40
 */
class GoodsPlan(id: Long? = null) : BaseDO(id, 0) {
    // 商品ID
    var goodsId: Long? = null

    // 用户ID
    var userId: Long? = null

    // 产品计划ID
    var productPlanId: Long? = null

    // 保司编码
    var partnerCode: String? = null

    // 保司名称
    var partnerName: String? = null

    // 一级大类
    var categoryName: String? = null

    // 二级大类
    var categorySubName: String? = null

    // 商品代码
    var goodsCode: String? = null

    // 商品名称
    var goodsName: String? = null

    // 商品状态
    var status: Int? = null

    // 是否可售
    var isForSale: Int? = null

    // 售卖开始时间
    var saleStartTime: Date? = null

    // 售卖结束时间
    var saleEndTime: Date? = null

    // 授权人
    var authorizer: String? = null

    // 授权时间
    var authorizeTime: Date? = null

    //佣金比例
    var comsRatio: Double? = null
}