package tech.chaosmin.framework.dao.dataobject

class PolicyByEvent(id: Long? = null) : BaseCommonDO(id) {
    var policyNo: String? = null
    var eventNo: String? = null
}