package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.BasicStatusEnum

/**
 * 产品计划
 * @author Romani min
 * @since 2020/12/8 15:57
 */
class GoodsPlan(id: Long? = null) : BaseCommonDO(id) {
    // 产品ID
    var goodsId: Long? = null

    // 责任包ID
    var packageId: Long? = null

    // 计划代码
    var planCode: String? = null

    // 计划名称
    var planName: String? = null

    // 计划状态
    var status: BasicStatusEnum? = null
}