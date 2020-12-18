package tech.chaosmin.framework.domain.response

/**
 * @author Romani min
 * @since 2020/12/18 13:19
 */
data class UserDetails(val departmentId: Long?, val name: String, val roles: List<String>)