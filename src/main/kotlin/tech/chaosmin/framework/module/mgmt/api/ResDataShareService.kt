package tech.chaosmin.framework.module.mgmt.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.module.mgmt.entity.response.ResDataResp

/**
 * @author Romani min
 * @since 2021/6/29 10:51
 */
@Api(tags = ["系统码表操作接口"], consumes = "application/json;charset=utf-8")
@RequestMapping("/event/res-data")
interface ResDataShareService {
    @GetMapping("/{channel}/{type}")
    fun list(@PathVariable("channel") channel: String, @PathVariable("type") type: List<String>): RestResult<List<ResDataResp>>
}