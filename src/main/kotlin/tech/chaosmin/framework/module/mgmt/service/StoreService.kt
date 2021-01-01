package tech.chaosmin.framework.module.mgmt.service

import org.springframework.security.core.Authentication
import tech.chaosmin.framework.module.mgmt.domain.auth.Rule

interface StoreService {
    fun fetchRuleWithComposeModes(authentication: Authentication): Rule

    fun store(username: String, authorities: Rule)

    fun clear(username: String)
}