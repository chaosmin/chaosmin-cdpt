package tech.chaosmin.framework.web.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/system")
class SystemController {
    @Value("\${useLocalCache:false}")
    private var useLocalCache = false

    @GetMapping(value = ["/config"])
    fun get(): Boolean {
        return useLocalCache
    }

    @RequestMapping("/ping-without-auth")
    fun health(): Health {
        return Health.up().build()
    }
}