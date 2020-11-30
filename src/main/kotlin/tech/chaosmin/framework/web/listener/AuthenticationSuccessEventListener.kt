package tech.chaosmin.framework.web.listener

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Component

/**
 * @author Romani min
 * @since 2020/11/30 17:08
 */
@Component
class AuthenticationSuccessEventListener : ApplicationListener<AuthenticationSuccessEvent> {
    private val logger = LoggerFactory.getLogger(AuthenticationSuccessEventListener::class.java)

    override fun onApplicationEvent(event: AuthenticationSuccessEvent) {
        logger.info("${event.authentication.name} login successful.")
    }
}