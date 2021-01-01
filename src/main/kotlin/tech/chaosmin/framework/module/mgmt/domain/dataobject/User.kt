package tech.chaosmin.framework.module.mgmt.domain.dataobject

import tech.chaosmin.framework.base.BaseDO

open class User(id: Long? = null) : BaseDO(id, 0) {
    // 部门id
    var departmentId: Long? = null

    // 用户名
    var username: String? = null

    // 登录账户
    var loginName: String? = null

    // 登录密码
    var password: String? = null

    // 用户状态
    var status: Int? = null

    // 电话
    var phone: String? = null

    // 邮件
    var email: String? = null
}