package tech.chaosmin.framework.dao.dataobject

/**
 * 费率公式表
 * @author Romani min
 * @since 2020/12/8 15:57
 */
class RateTableFormula(id: Long? = null) : BaseCommonDO(id) {
    // 费率表ID
    var rateTableId: Long? = null

    // 费率表代码
    var rateTableCode: String? = null

    // 费率表公式
    var formula: String? = null
}