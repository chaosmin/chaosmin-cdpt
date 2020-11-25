//package tech.chaosmin.framework.service.impl
//
//import org.springframework.stereotype.Service
//import tech.chaosmin.framework.exception.AuthenticationException
//import tech.chaosmin.framework.service.AuthenticateService
//
//@Service
//class SimpleAuthenticateService : AuthenticateService {
//
//    private val users = listOf("admin", "reader")
//
//    @Throws(AuthenticationException::class)
//    override fun authenticate(username: String, password: String) {
//        if (users.contains(username) && password == username.reversed()) {
//            // do nothing
//        } else {
//            throw AuthenticationException("username or password error")
//        }
//    }
//}