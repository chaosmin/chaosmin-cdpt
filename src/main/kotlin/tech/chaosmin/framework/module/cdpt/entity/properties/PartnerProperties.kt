/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PartnerProperties.kt
 *
 * The basic configuration data collection of the cooperating insurance company.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.entity.properties

import cn.hutool.http.Method
import tech.chaosmin.framework.base.enums.PolicyProcessEnum

/**
 * @author Romani min
 * @since 2021/6/21 13:27
 */
open class PartnerProperties {
    // 服务请求地址
    var server: String? = null

    // 秘钥
    var accessKey: String? = null

    // 公钥
    var publicKey: String? = null

    // 私钥
    var securityKey: String? = null

    // 接口合计
    var apiList: List<Api>? = null

    class Api {
        // 投保流程
        var process: PolicyProcessEnum? = null

        // 请求方式
        var method: Method? = null

        // 请求链接
        var url: String? = null
    }
}