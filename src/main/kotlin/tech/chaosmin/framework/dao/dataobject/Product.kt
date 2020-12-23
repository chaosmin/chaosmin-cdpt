package tech.chaosmin.framework.dao.dataobject

/**
 * 保险产品定义
 * @author Romani min
 * @since 2020/12/23 11:06
 */
class Product(id: Long? = null) : BaseCommonDO(id, 0) {
    // 保司ID
    var partnerId: Long? = null

    // 产品代码
    var productCode: String? = null

    // 产品名称
    var productName: String? = null

    // 产品子名称
    var productSubName: String? = null

    // 保司产品代码
    var partnerProductNo: String? = null

    // 产品描述
    var productDesc: String? = null

    // 状态
    var status: Int? = null
}