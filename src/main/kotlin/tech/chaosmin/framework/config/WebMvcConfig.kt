package tech.chaosmin.framework.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import tech.chaosmin.framework.web.interceptor.AuthInterceptor
import tech.chaosmin.framework.web.interceptor.LimitInterceptor

@Configuration

open class WebMvcConfig(
    private val authInterceptor: AuthInterceptor,
    private val limitInterceptor: LimitInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/auth/**")
            .excludePathPatterns("/event/**")
            .excludePathPatterns("/ddl-change")
            .excludePathPatterns("/system/ping-without-auth")
        registry.addInterceptor(limitInterceptor)
            .addPathPatterns("/**")
    }
}