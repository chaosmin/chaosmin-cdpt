package tech.chaosmin.framework.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import tech.chaosmin.framework.domain.auth.Action
import tech.chaosmin.framework.domain.auth.AnonymousAuthentication
import tech.chaosmin.framework.domain.auth.Authentication
import tech.chaosmin.framework.domain.auth.ComposeMode
import tech.chaosmin.framework.exception.AuthenticationException
import tech.chaosmin.framework.service.AuthService
import tech.chaosmin.framework.service.AuthenticateService
import tech.chaosmin.framework.service.StoreService
import tech.chaosmin.framework.service.TokenService
import java.util.function.Predicate

@Service
class AuthServiceImpl(
    private val authenticateService: AuthenticateService,
    private val tokenService: TokenService,
    private val storeService: StoreService,
    @Value("\${server.global-anonymous:false}") @Volatile override var globalAnonymous: Boolean
) : AuthService {
    @Throws(AuthenticationException::class)
    override fun authenticate(username: String, password: String): Authentication {
        authenticateService.authenticate(username, password)
        return tokenService.create(username)
    }

    @Throws(AuthenticationException::class)
    override fun authenticateByToken(token: String): Authentication = tokenService.check(token)

    @Throws(AuthenticationException::class)
    override fun authenticateByAnonymous(): Authentication {
        if (globalAnonymous) {
            return AnonymousAuthentication
        } else {
            throw AuthenticationException("not allow anonymous")
        }
    }

    override fun authorize(authentication: Authentication): Predicate<Action> {
        return authentication.roles.map { role ->
            storeService.fetchRuleWithComposeModesByRole(role)
                .map { (rule, composeMode) -> rule.toPredicate() to composeMode }
                .reduce { (predicate, composeMode), (nextPredicate, nextComposeMode) ->
                    when (composeMode) {
                        ComposeMode.AND -> predicate.and(nextPredicate)
                        ComposeMode.OR -> predicate.or(nextPredicate)
                    } to nextComposeMode
                }.first
        }.reduce { predicate, nextPredicate -> predicate.or(nextPredicate) }
    }

    override fun invalid(token: String) {
        tokenService.invalid(token)
    }
}