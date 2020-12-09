package tech.chaosmin.framework.dao.dataobject

import tech.chaosmin.framework.domain.enums.YesNoEnum

/**
 * 产品责任包
 * @author Romani min
 * @since 2020/12/8 15:57
 */
class PackageLiability(id: Long? = null) : BaseCommonDO(id) {
    // 包ID
    var packageId: Long? = null

    // 产品ID
    var goodsId: Long? = null

    // 责任ID
    var liabilityId: Long? = null

    // 责任代码
    var liabilityCode: String? = null

    // 责任名称
    var liabilityName: String? = null

    // 责任分类
    var liabilityCategory: String? = null

    // 责任备注
    var liabilityRemark: String? = null

    // 责任顺序
    var sort: Int? = null

    // 是否唯一
    var isOptional: YesNoEnum? = null

    // 金额
    var amount: String? = null

    // 金额备注
    var amountRemark: String? = null
}