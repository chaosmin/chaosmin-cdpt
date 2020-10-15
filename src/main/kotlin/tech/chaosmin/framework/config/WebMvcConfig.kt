package tech.chaosmin.framework.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import tech.chaosmin.framework.web.interceptor.AuthInterceptor

@Configuration
open class WebMvcConfig(
    private val authInterceptor: AuthInterceptor,
    @Value("\${server.interceptor.debug:false}") @Volatile var interceptorDebug: Boolean
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        if (!interceptorDebug) {
            registry
                .addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**")
                .excludePathPatterns("/event/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/ddl-change")
                .excludePathPatterns("/system/ping-without-auth")
        }
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        // 允许跨域访问的路径
        registry.addMapping("/**")
            // 允许跨域访问的源
            .allowedOrigins("*")
            // 允许请求方法
            .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
            // 预检间隔时间
            .maxAge(168000)
            // 允许头部设置
            .allowedHeaders("*")
            // 是否发送cookie
            .allowCredentials(true)
    }
}