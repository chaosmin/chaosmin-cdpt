package tech.chaosmin.framework.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import tech.chaosmin.framework.definition.ServerInterceptorParam
import tech.chaosmin.framework.web.interceptor.AuthInterceptor

@Configuration

open class WebMvcConfig(
    private val authInterceptor: AuthInterceptor,
    private val serverInterceptorParam: ServerInterceptorParam
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        val list = serverInterceptorParam.except.toTypedArray()
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(*list)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        // 允许跨域访问的路径
        registry.addMapping("/**")
            // 允许跨域访问的源
            .allowedOrigins("*")
            // 允许请求方法
            .allowedMethods("HEAD", "POST", "GET", "PUT", "OPTIONS", "DELETE")
            // 预检间隔时间
            .maxAge(168000)
            // 允许头部设置
            .allowedHeaders("*")
            // 是否发送cookie
            .allowCredentials(true)
    }
}