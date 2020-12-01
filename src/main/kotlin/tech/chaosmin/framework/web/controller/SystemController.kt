package tech.chaosmin.framework.web.controller

import org.springframework.boot.actuate.health.Health
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/system")
class SystemController {
    @RequestMapping("/ping-without-auth")
    fun health(): Health {
        return Health.up().build()
    }
}