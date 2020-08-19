package tech.chaosmin.framework

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("tech.chaosmin.framework.dao")
@SpringBootApplication
open class Application {
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
