package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.BasicStatusEnum
import tech.chaosmin.framework.domain.enums.YesNoEnum
import java.util.*

class Goods(id: Long? = null) : BaseCommonDO(id) {
    var partnerId: Long? = null
    var goodsCode: String? = null
    var goodsName: String? = null
    var goodsSubName: String? = null
    var goodsCategory: Long? = null
    var goodsDesc: String? = null
    var status: BasicStatusEnum? = null
    var isForSale: YesNoEnum? = null
    var saleStartTime: Date? = null
    var saleEndTime: Date? = null
    var showStartTime: Date? = null
    var showEndTime: Date? = null
}