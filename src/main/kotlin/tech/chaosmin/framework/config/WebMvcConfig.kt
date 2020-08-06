package tech.chaosmin.framework.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import tech.chaosmin.framework.web.interceptor.AuthInterceptor

@Configuration
open class WebMvcConfig(private val authInterceptor: AuthInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/auth/**")
            .excludePathPatterns("/event/**")
            .excludePathPatterns("/ddl-change")
            .excludePathPatterns("/system/ping-without-auth")
    }
}