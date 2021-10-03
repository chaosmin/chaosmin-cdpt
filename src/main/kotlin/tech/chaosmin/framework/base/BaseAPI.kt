/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * BaseAPI.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.base

import com.baomidou.mybatisplus.core.metadata.IPage
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2021/1/1 14:16
 */
interface BaseAPI<RE : BaseReq, RQ : BaseResp> {
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    fun selectById(@PathVariable("id") id: Long): RestResult<RQ>

    @GetMapping
    @ApiOperation(value = "分页查询查询")
    fun page(request: HttpServletRequest): RestResult<IPage<RQ>>

    @PostMapping
    @ApiOperation(value = "创建/新增")
    fun save(@RequestBody req: RE): RestResult<RQ>

    @PutMapping("/{id}")
    @ApiOperation(value = "修改/更新")
    fun update(@PathVariable("id") id: Long, @RequestBody req: RE): RestResult<RQ>

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    fun delete(@PathVariable("id") id: Long): RestResult<RQ>
}