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

    // 联系地址
    var address: String? = null

    // 投保机构名称
    var departmentName: String? = null

    // 投保机构证件号
    var departmentCerti: String? = null

    // 支付方式
    var payType: Int? = null
}