package tech.chaosmin.framework.service

interface RoleService {
    fun findRoles(userId: Long): Set<Long>
}