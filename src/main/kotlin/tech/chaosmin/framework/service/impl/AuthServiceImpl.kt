package tech.chaosmin.framework.service.impl

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import tech.chaosmin.framework.domain.auth.Action
import tech.chaosmin.framework.service.AuthService
import tech.chaosmin.framework.service.StoreService
import java.util.function.Predicate

@Service
class AuthServiceImpl(private val storeService: StoreService) : AuthService {
    override fun authorize(authentication: Authentication): Predicate<Action> {
        return storeService.fetchRuleWithComposeModes(authentication).toPredicate()
    }

    override fun invalid(username: String) {
        storeService.clear(username)
    }
}