package tech.chaosmin.framework.module.cdpt.domain.dataobject

import com.baomidou.mybatisplus.annotation.TableName
import tech.chaosmin.framework.base.BaseDO

/**
 * 产品计划责任
 * @author Romani min
 * @since 2020/12/23 11:08
 */
@TableName("plan_liability")
open class ProductPlanLib(id: Long? = null) : BaseDO(id, 0) {
    // 计划ID
    var productPlanId: Long? = null

    // 计划code
    var productPlanCode: String? = null

    // 责任类名
    var liabilityCategory: String? = null

    // 责任名
    var liabilityName: String? = null

    // 责任备注
    var liabilityRemark: String? = null

    // 排序
    var sort: Int? = null

    // 责任金额
    var amount: String? = null

    // 金额备注
    var amountRemark: String? = null
}