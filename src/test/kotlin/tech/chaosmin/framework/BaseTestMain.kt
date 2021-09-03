package tech.chaosmin.framework

import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [Application::class])
open class BaseTestMain {
    init {
        System.setProperty("DEPLOY_ENV", "dev")
        System.setProperty("NACOS_GROUP", "chaosmin")
        System.setProperty("NACOS_ENABLE", "true")
        System.setProperty("SPRING_DISCOVERY_ENABLE", "false")
        System.setProperty("NACOS_SERVER_URL", "106.14.40.2:8848")
    }
}