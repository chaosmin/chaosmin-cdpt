package tech.chaosmin.framework.dao.dataobject

class Customer(id: Long? = null) : BaseCommonDO(id) {
    var partyId: Long? = null
    var personId: Long? = null
}