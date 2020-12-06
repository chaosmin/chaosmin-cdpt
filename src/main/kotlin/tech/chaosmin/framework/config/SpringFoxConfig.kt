package tech.chaosmin.framework.config

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import tech.chaosmin.framework.domain.const.ApplicationParam
import tech.chaosmin.framework.domain.const.SwaggerParam
import tech.chaosmin.framework.domain.const.SystemConst.INIT_SUCCESSFULLY
import tech.chaosmin.framework.utils.JsonUtil
import java.util.*

@Configuration
@Import(BeanValidatorPluginsConfiguration::class)
@ConditionalOnProperty(prefix = "swagger", value = ["enable"], havingValue = "true", matchIfMissing = false)
open class SpringFoxConfig(
    private val applicationParam: ApplicationParam,
    private val swaggerParam: SwaggerParam
) {
    private val logger = LoggerFactory.getLogger(SpringFoxConfig::class.java)

    @Bean
    open fun apiDocket(): Docket {
        if (swaggerParam.enable) {
            logger.info("Application param information: \n${JsonUtil.encode(applicationParam, true)}")
            logger.info("Swagger param information: \n${JsonUtil.encode(swaggerParam, true)}")
        }
        val docket = Docket(DocumentationType.SWAGGER_2)
            .enable(swaggerParam.enable)
            .select()
            .apis(RequestHandlerSelectors.basePackage(swaggerParam.packages))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
        logger.info(INIT_SUCCESSFULLY, docket.javaClass.name)
        return docket
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            applicationParam.name, applicationParam.description, applicationParam.version, swaggerParam.contact.website,
            Contact(swaggerParam.contact.name, swaggerParam.contact.url, swaggerParam.contact.email),
            applicationParam.license, applicationParam.licenseUrl, Collections.emptyList()
        )
    }
}