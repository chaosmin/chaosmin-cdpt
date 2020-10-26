package tech.chaosmin.framework.dao.dataobject

class GoodsAgreement(id: Long? = null) : BaseCommonDO(id) {
    var goodsId: Long? = null
    var notice: String? = null
    var announcement: String? = null
    var specialAgreement: String? = null
    var disputeSettlement: String? = null
    var jurisdiction: String? = null
    var healthDeclaration: String? = null
    var insuranceCase: String? = null
}