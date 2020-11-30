package tech.chaosmin.framework.web.listener

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent
import org.springframework.stereotype.Component

/**
 * @author Romani min
 * @since 2020/11/30 17:11
 */
@Component
class AuthenticationFailureEventListener(private val stringRedisTemplate: StringRedisTemplate) :
    ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private val logger = LoggerFactory.getLogger(AuthenticationFailureEventListener::class.java)

    override fun onApplicationEvent(event: AuthenticationFailureBadCredentialsEvent) {
        logger.warn("${event.authentication.name} failed to login.")
    }
}