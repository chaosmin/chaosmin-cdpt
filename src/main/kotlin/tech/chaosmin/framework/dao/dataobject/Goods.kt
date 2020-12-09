package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.BasicStatusEnum
import tech.chaosmin.framework.domain.enums.YesNoEnum
import java.util.*

/**
 * 保险产品(包含计划责任等可销售)
 * @author Romani min
 * @since 2020/12/8 15:57
 */
class Goods(id: Long? = null) : BaseCommonDO(id) {
    // 保司ID
    var partnerId: Long? = null

    // 产品代码
    var goodsCode: String? = null

    // 产品名称
    var goodsName: String? = null

    // 产品子名称
    var goodsSubName: String? = null

    // 产品分类
    var goodsCategory: Long? = null

    // 产品描述
    var goodsDesc: String? = null

    // 产品状态
    var status: BasicStatusEnum? = null

    // 是否可售
    var isForSale: YesNoEnum? = null

    // 销售开始时间
    var saleStartTime: Date? = null

    // 销售结束时间
    var saleEndTime: Date? = null

    // 展示开始时间
    var showStartTime: Date? = null

    // 展示结束时间
    var showEndTime: Date? = null
}