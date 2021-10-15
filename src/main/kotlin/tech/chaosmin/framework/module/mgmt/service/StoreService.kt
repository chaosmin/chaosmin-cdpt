package tech.chaosmin.framework.module.mgmt.service

import org.springframework.security.core.Authentication
import tech.chaosmin.framework.module.mgmt.domain.auth.JwtUserDetails
import tech.chaosmin.framework.module.mgmt.domain.auth.Rule

interface StoreService {
    fun fetchJwtUserDetail(username: String): JwtUserDetails

    fun fetchRuleWithComposeModes(authentication: Authentication): Rule

    fun clear(cacheName: String)
}