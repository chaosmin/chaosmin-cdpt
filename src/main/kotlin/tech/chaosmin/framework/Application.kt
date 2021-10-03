package tech.chaosmin.framework

import org.mybatis.spring.annotation.MapperScan
import org.slf4j.LoggerFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.client.RestTemplate
import springfox.documentation.oas.annotations.EnableOpenApi

@EnableCaching
@EnableOpenApi
@EnableDiscoveryClient
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("tech.chaosmin.framework.module.*.domain.dao")
@Import(cn.hutool.extra.spring.SpringUtil::class)
@SpringBootApplication
open class Application {
    @Bean
    @LoadBalanced
    open fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    open fun createMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    fun start(args: Array<String>) {
        val logger = LoggerFactory.getLogger(Application::class.java)
        val app = SpringApplication.run(Application::class.java, *args)
        logger.error("Application[${app.id}] is ${if (app.isRunning) "running" else "stop"} now.")
    }
}

fun main(args: Array<String>) = Application().start(args)