package tech.chaosmin.framework.service

import org.springframework.security.core.Authentication
import tech.chaosmin.framework.domain.auth.ComposeMode
import tech.chaosmin.framework.domain.auth.Rule

interface StoreService {
    fun fetchRuleWithComposeModes(authentication: Authentication): List<Pair<Rule, ComposeMode>>

    fun store(username: String, authorities: List<Pair<Rule, ComposeMode>>)

    fun clear(username: String)
}