/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * LetterHeadAPI.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.mgmt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.mgmt.entity.response.LetterHeadResp

/**
 * @author Romani min
 * @since 2021/10/21 13:42
 */
@Api(tags = ["抬头操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/\${application.version}/api/letter-head")
interface LetterHeadAPI {
    @GetMapping("user/{userId}")
    fun getByUser(@PathVariable("userId") userId: Long): RestResult<List<LetterHeadResp>>
}