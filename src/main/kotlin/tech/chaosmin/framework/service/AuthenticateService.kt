package tech.chaosmin.framework.service

import tech.chaosmin.framework.exception.AuthenticationException

interface AuthenticateService {
    @Throws(AuthenticationException::class)
    fun authenticate(username: String, password: String)
}