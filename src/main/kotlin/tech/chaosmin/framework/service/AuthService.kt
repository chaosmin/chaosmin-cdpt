package tech.chaosmin.framework.service

import tech.chaosmin.framework.domain.auth.Action
import tech.chaosmin.framework.domain.auth.Authentication
import tech.chaosmin.framework.exception.AuthenticationException
import java.util.function.Predicate

interface AuthService {
    var globalAnonymous: Boolean

    @Throws(AuthenticationException::class)
    fun authenticate(username: String, password: String): Authentication

    @Throws(AuthenticationException::class)
    fun authenticateByToken(token: String): Authentication

    @Throws(AuthenticationException::class)
    fun authenticateByAnonymous(): Authentication

    fun authorize(authentication: Authentication): Predicate<Action>

    fun invalid(token: String)

    fun canAccess(authentication: Authentication, action: Action): Boolean = authorize(authentication).test(action)
}