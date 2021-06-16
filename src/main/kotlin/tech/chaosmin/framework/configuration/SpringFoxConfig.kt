package tech.chaosmin.framework.configuration

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringBootVersion
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import tech.chaosmin.framework.definition.ApplicationParam
import tech.chaosmin.framework.definition.SwaggerParam
import tech.chaosmin.framework.definition.SystemConst.INIT_SUCCESSFULLY
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.JwtTokenUtil
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
        val docket = Docket(DocumentationType.OAS_30)
            .enable(swaggerParam.enable)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage(swaggerParam.packages))
            .paths(PathSelectors.any())
            .build()
            .protocols(setOf("http", "https"))
            .securitySchemes(securitySchemes())
            .securityContexts(securityContexts())
        logger.info(INIT_SUCCESSFULLY, docket.javaClass.name)
        return docket
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title(applicationParam.name)
            .description("<b>Application description:</b> ${applicationParam.description}<p><b>Springboot Version:</b> ${SpringBootVersion.getVersion()}")
            .termsOfServiceUrl("http://localhost:8080/")
            .contact(Contact(swaggerParam.contact.name, swaggerParam.contact.url, swaggerParam.contact.email))
            .version(applicationParam.version)
            .build()
    }

    private fun securitySchemes(): List<SecurityScheme> {
        return Collections.singletonList(
            HttpAuthenticationScheme.JWT_BEARER_BUILDER
                .name(JwtTokenUtil.TOKEN_HEADER)
                .description("JWT Token for access service.")
                .build()
        )
    }

    private fun securityContexts(): List<SecurityContext> {
        return Collections.singletonList(
            SecurityContext.builder()
                .securityReferences(
                    Collections.singletonList(
                        SecurityReference(
                            JwtTokenUtil.TOKEN_HEADER,
                            arrayOf(AuthorizationScope("global", "Access everything."))
                        )
                    )
                ).build()
        )
    }
}