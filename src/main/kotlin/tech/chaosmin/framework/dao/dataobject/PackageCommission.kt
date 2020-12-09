package tech.chaosmin.framework.dao.dataobject

/**
 * 产品佣金包
 * @author Romani min
 * @since 2020/12/8 15:57
 */
class PackageCommission(id: Long? = null) : BaseCommonDO(id) {
    // 包ID
    var packageId: Long? = null

    // 产品ID
    var goodsId: Long? = null

    // 佣金比例
    var rate: Double? = null
}