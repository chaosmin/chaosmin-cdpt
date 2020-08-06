package tech.chaosmin.framework.service.impl

import org.springframework.stereotype.Service
import tech.chaosmin.framework.domain.auth.LoginAuthentication
import tech.chaosmin.framework.exception.AuthenticationException
import tech.chaosmin.framework.service.StoreService
import tech.chaosmin.framework.service.TokenService
import java.util.*

@Service
class TokenServiceImpl(private val storeService: StoreService) : TokenService {
    override fun create(username: String): LoginAuthentication {
        val token = UUID.randomUUID().toString().replace("-", "")
        val roles = storeService.fetchRolesByUsername(username)
        val rolesExpr = roles.joinToString(separator = ",") { it }
        storeService.store(token, roles, rolesExpr)
        return LoginAuthentication(username, roles, token)
    }

    @Throws(AuthenticationException::class)
    override fun check(token: String): LoginAuthentication {
        return storeService.check(token)
    }

    override fun invalid(token: String) {
        storeService.invalid(token)
    }
}
