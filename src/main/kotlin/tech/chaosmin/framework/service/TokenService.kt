//package tech.chaosmin.framework.service
//
//import tech.chaosmin.framework.domain.auth.LoginAuthentication
//import tech.chaosmin.framework.exception.AuthenticationException
//
//interface TokenService {
//    fun create(username: String): LoginAuthentication
//
//    @Throws(AuthenticationException::class)
//    fun check(token: String): LoginAuthentication
//
//    fun invalid(token: String)
//}