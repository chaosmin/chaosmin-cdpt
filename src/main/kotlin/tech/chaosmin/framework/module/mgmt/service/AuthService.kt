package tech.chaosmin.framework.module.mgmt.service

import org.springframework.security.core.Authentication
import tech.chaosmin.framework.module.mgmt.domain.auth.Action
import java.util.function.Predicate

interface AuthService {
    fun authorize(authentication: Authentication): Predicate<Action>

    fun invalid(username: String)

    fun canAccess(authentication: Authentication, action: Action): Boolean = authorize(authentication).test(action)
}