package tech.chaosmin.framework.dao.dataobject

class Role(id: Long? = null) : BaseCommonDO(id, 0) {
    private var code: String? = null
    private var name: String? = null
    private var priority: Int? = null
}