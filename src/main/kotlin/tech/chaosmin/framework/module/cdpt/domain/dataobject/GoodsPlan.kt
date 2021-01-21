package tech.chaosmin.framework.module.cdpt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/31 11:40
 */
open class GoodsPlan(id: Long? = null) : BaseDO(id, 0) {
    // 部门(机构)名称
    var departmentName: String? = null

    // 角色名称
    var roleName: String? = null

    // 用户ID
    var userId: Long? = null

    // 用户名
    var userName: String? = null

    // 产品ID
    var productId: Long? = null

    // 产品计划ID
    var productPlanId: Long? = null

    // 保司编码
    var partnerCode: String? = null

    // 保司名称
    var partnerName: String? = null

    // 商品状态
    var status: Int? = null

    // 是否可售
    var isForSale: Int? = null

    // 售卖开始时间
    var saleStartTime: Date? = null

    // 售卖结束时间
    var saleEndTime: Date? = null

    // 授权时间
    var authorizeTime: Date? = null

    // 授权人ID
    var authorizerId: Long? = null

    // 授权人
    var authorizer: String? = null

    //佣金比例
    var comsRatio: Double? = null
}