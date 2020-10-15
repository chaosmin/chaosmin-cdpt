package tech.chaosmin.framework.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.RequestParameterBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.service.RequestParameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
@Import(BeanValidatorPluginsConfiguration::class)
@ConditionalOnProperty(prefix = "swagger", value = ["enable"], havingValue = "true", matchIfMissing = false)
open class SpringFoxConfig {
    private val logger = LoggerFactory.getLogger(SpringFoxConfig::class.java)

    @Value("\${spring.application.name:TBD}")
    lateinit var title: String

    @Value("\${spring.application.version:TBD}")
    lateinit var version: String

    @Value("\${spring.application.description:TBD}")
    lateinit var description: String

    @Value("\${spring.application.license:TBD}")
    lateinit var license: String

    @Value("\${spring.application.license-url:TBD}")
    lateinit var licenseUrl: String

    @Value("\${contact.name:TBD}")
    lateinit var contactName: String

    @Value("\${contact.url:TBD}")
    lateinit var contactUrl: String

    @Value("\${contact.email:TBD}")
    lateinit var contactEmail: String

    @Value("\${contact.website:TBD}")
    lateinit var website: String

    @Value("\${swagger.enable:false}")
    var show: Boolean = false

    @Value("\${swagger.packages:TBD}")
    lateinit var packages: String

    @Bean
    open fun apiDocket(): Docket {
        logger.info(">> init apiDocket.")
        val parameterBuilder =  RequestParameterBuilder()
        val parameters = ArrayList<RequestParameter>()
        parameterBuilder.name("Authorization").description("令牌").required(false).build()
        parameters.add(parameterBuilder.build())
        return Docket(DocumentationType.OAS_30)
            .enable(show)
            .select()
            .apis(RequestHandlerSelectors.basePackage(packages))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .globalRequestParameters(parameters)
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            title, description, version, website,
            Contact(contactName, contactUrl, contactEmail),
            license, licenseUrl, Collections.emptyList()
        )
    }
}