package tech.chaosmin.framework.dao.dataobject

/**
 * 保险公司
 * @author Romani min
 * @since 2020/12/23 11:02
 */
class Partner(id: Long? = null) : BaseCommonDO(id, 0) {
    // 保司编码
    var partnerCode: String? = null

    // 保司名称
    var partnerName: String? = null

    // 保司公钥
    var publicKey: String? = null
}