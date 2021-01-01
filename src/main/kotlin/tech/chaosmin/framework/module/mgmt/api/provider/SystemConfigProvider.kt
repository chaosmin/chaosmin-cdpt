package tech.chaosmin.framework.module.mgmt.api.provider

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

/**
 * @author Romani min
 * @since 2020/12/25 18:45
 */
@RestController
@RequestMapping("/config")
@RefreshScope
open class SystemConfigProvider {
    @Value("\${useLocalCache:false}")
    private val useLocalCache = false

    @ApiIgnore
    @RequestMapping("/get")
    fun get(): Boolean {
        return useLocalCache
    }
}