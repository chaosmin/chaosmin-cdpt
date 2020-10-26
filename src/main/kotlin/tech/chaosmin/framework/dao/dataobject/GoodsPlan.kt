package tech.chaosmin.framework.dao.dataobject

class GoodsPlan(id: Long? = null) : BaseCommonDO(id) {
    var goodsId: Long? = null
    var packageId: Long? = null
    var planCode: String? = null
    var planName: String? = null
}