//package tech.chaosmin.framework.service
//
//import tech.chaosmin.framework.domain.auth.ComposeMode
//import tech.chaosmin.framework.domain.auth.LoginAuthentication
//import tech.chaosmin.framework.domain.auth.Rule
//
//interface StoreService {
//    fun fetchRolesByUsername(username: String): List<String>
//    fun fetchRuleWithComposeModesByRole(role: String): List<Pair<Rule, ComposeMode>>
//
//    fun store(token: String, roles: List<String>, rolesExpr: String)
//    fun check(token: String): LoginAuthentication
//    fun invalid(token: String)
//}