package tech.chaosmin.framework.dao.dataobject

open class Department(id: Long? = null) : BaseCommonDO(id, 0) {
    // 部门编码
    var code: String? = null

    // 部门名称
    var name: String? = null

    // 部门状态
    var status: Int? = null
}