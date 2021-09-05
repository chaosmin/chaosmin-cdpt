/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PolicyTraceAPI.java
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.BaseAPI
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyTraceReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyTraceResp

/**
 * @author Romani min
 * @since 2021/9/5 10:38
 */
@Api(tags = ["保单可回溯信息操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/policy-trace")
interface PolicyTraceAPI : BaseAPI<PolicyTraceReq, PolicyTraceResp>