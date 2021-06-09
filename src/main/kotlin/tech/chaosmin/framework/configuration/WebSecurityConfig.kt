package tech.chaosmin.framework.configuration

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.header.Header
import org.springframework.security.web.header.writers.StaticHeadersWriter
import tech.chaosmin.framework.module.mgmt.service.impl.JwtAuthenticationImpl
import tech.chaosmin.framework.utils.JwtTokenUtil
import tech.chaosmin.framework.web.filter.AccessLogFilter
import tech.chaosmin.framework.web.filter.JWTAuthenticationFilter
import tech.chaosmin.framework.web.filter.JWTAuthorizationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class WebSecurityConfig(@Qualifier("userDetailsServiceImpl") private val userDetailsService: UserDetailsService) :
    WebSecurityConfigurerAdapter() {
    private val logger = LoggerFactory.getLogger(AccessLogFilter::class.java)

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Bean
    open fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        // 使用自定义登录身份认证组件
        auth.authenticationProvider(JwtAuthenticationImpl(userDetailsService, passwordEncoder()))
    }

    override fun configure(http: HttpSecurity) {
        // logger.info("WebSecurityConfig.globalAnonymous switch: $globalAnonymous")
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/auth/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTAuthenticationFilter(authenticationManager()))
            .addFilter(JWTAuthorizationFilter(authenticationManager()))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.headers().addHeaderWriter(
            StaticHeadersWriter(
                listOf(
                    // 支持所有源的访问
                    Header("Access-control-Allow-Origin", "*"),
                    // 使ajax请求能够取到header中的jwt token信息
                    Header("Access-Control-Expose-Headers", JwtTokenUtil.TOKEN_HEADER)
                )
            )
        )
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**",
            "/cCo14UwY86DZnm9k/actuator/**"
        )
    }
}