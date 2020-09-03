package tech.chaosmin.framework.service

interface AuthorityService {
    fun findAuthorities(roleIds: Set<Long>): Set<String>
}