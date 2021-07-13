package tech.chaosmin.framework.module.mgmt.service

import org.springframework.security.core.Authentication
import tech.chaosmin.framework.module.mgmt.domain.auth.Rule

interface StoreService {
    fun fetchRuleWithComposeModes(authentication: Authentication): Rule

    fun store(cacheName: String, authorities: Rule)

    fun clear(cacheName: String)
}