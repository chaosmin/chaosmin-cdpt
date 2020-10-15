package tech.chaosmin.framework.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import tech.chaosmin.framework.provider.JwtAuthenticationProvider
import tech.chaosmin.framework.web.filter.AccessLogFilter
import tech.chaosmin.framework.web.filter.JwtLoginFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class WebSecurityConfig(
    @Value("\${server.interceptor.debug:false}") @Volatile var interceptorDebug: Boolean,
    @Qualifier("userDetailsServiceImpl") private val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter() {
    private val log: Logger = LoggerFactory.getLogger(AccessLogFilter::class.java)

    override fun configure(auth: AuthenticationManagerBuilder) {
        // 使用自定义登录身份认证组件
        auth.authenticationProvider(JwtAuthenticationProvider(userDetailsService, passwordEncoder()))
    }

    override fun configure(http: HttpSecurity) {
        if (interceptorDebug) {
            log.info("Interceptor function has been deactivated.")
            http.cors().and().csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll().and().logout().permitAll();
        } else {
            // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
            http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/login")
            // 退出登录处理器
            http.logout().logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler())
            // 开启登录认证流程过滤器
            http.addFilterBefore(
                JwtLoginFilter(authenticationManager(), interceptorDebug),
                UsernamePasswordAuthenticationFilter::class.java
            )
        }
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Bean
    open fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}