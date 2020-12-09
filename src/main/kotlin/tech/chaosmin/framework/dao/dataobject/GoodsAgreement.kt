package tech.chaosmin.framework.dao.dataobject

/**
 * 保险产品特约须知及其他声明
 * @author Romani min
 * @since 2020/12/8 15:57
 */
class GoodsAgreement(id: Long? = null) : BaseCommonDO(id) {
    // 产品ID
    var goodsId: Long? = null

    // 投保须知
    var notice: String? = null

    // 投被保人声明与授权
    var announcement: String? = null

    // 特别约定
    var specialAgreement: String? = null

    // 争议处理
    var disputeSettlement: String? = null

    // 司法管辖
    var jurisdiction: String? = null

    // 健康告知
    var healthDeclaration: String? = null

    // 保险案例
    var insuranceCase: String? = null
}