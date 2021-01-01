package tech.chaosmin.framework

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.EnableTransactionManagement
import springfox.documentation.oas.annotations.EnableOpenApi

@EnableCaching
@EnableOpenApi
@EnableDiscoveryClient
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("tech.chaosmin.framework.module.*.domain.dao")
@Import(cn.hutool.extra.spring.SpringUtil::class)
@SpringBootApplication
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
