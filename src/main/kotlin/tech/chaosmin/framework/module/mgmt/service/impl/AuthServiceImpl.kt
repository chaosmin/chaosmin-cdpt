package tech.chaosmin.framework.module.mgmt.service.impl

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.domain.auth.Action
import tech.chaosmin.framework.module.mgmt.service.AuthService
import tech.chaosmin.framework.module.mgmt.service.StoreService
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