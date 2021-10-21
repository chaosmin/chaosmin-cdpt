package tech.chaosmin.framework.module.mgmt.service.impl

import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.UserStatusEnum
import tech.chaosmin.framework.definition.SystemConst.APPLICATION_NAME
import tech.chaosmin.framework.definition.SystemConst.DEFAULT_CACHE_EXPIRE_TIME
import tech.chaosmin.framework.definition.SystemConst.HTTP_METHOD
import tech.chaosmin.framework.definition.SystemConst.REQUEST_URL
import tech.chaosmin.framework.exception.PermissionException
import tech.chaosmin.framework.module.cdpt.entity.enums.PayTypeEnum
import tech.chaosmin.framework.module.mgmt.domain.auth.GrantedAuthorityImpl
import tech.chaosmin.framework.module.mgmt.domain.auth.JwtUserDetails
import tech.chaosmin.framework.module.mgmt.domain.auth.Rule
import tech.chaosmin.framework.module.mgmt.service.AuthorityService
import tech.chaosmin.framework.module.mgmt.service.RoleService
import tech.chaosmin.framework.module.mgmt.service.StoreService
import tech.chaosmin.framework.module.mgmt.service.UserService
import tech.chaosmin.framework.utils.EnumClient
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

@Service
class StoreServiceImpl(
    private val userService: UserService,
    private val roleService: RoleService,
    private val authorityService: AuthorityService
) : StoreService {
    private val logger = LoggerFactory.getLogger(StoreService::class.java)

    @Resource
    lateinit var redisTemplate: RedisTemplate<String, Rule>

    override fun fetchJwtUserDetail(username: String): JwtUserDetails {
        synchronized(this) {
            return userService.findByLoginName(username)?.run {
                val roles = roleService.findRoles(this.id!!)
                if (roles.isEmpty()) {
                    throw PermissionException(ErrorCodeEnum.NO_PERMISSION.code)
                }
                val roleCodes = roles.mapNotNull { it.code }
                val payType = EnumClient.getEnum(PayTypeEnum::class.java, this.payType ?: 0)?.name
                val accountNonExpired = this.status != null && this.status == UserStatusEnum.VALID.getCode()
                val credentialsNonExpired = true
                val accountNonLocked = true

                // 在redis缓存创建用户权限
                val authorities = authorityService.findAuthorities(roles.map { it.id!! }.toSet()).map { GrantedAuthorityImpl(it.authority) }
                    .filterNot { it.authority.startsWith("null") }.distinctBy { it.authority }
                store(username, convert(authorities))

                JwtUserDetails(
                    this.id!!, username, this.password!!, payType,
                    roleCodes, accountNonExpired && credentialsNonExpired && accountNonLocked,
                    accountNonExpired, credentialsNonExpired, accountNonLocked
                )
            } ?: throw UsernameNotFoundException(ErrorCodeEnum.USER_NOT_FOUND.code)
        }
    }

    override fun fetchRuleWithComposeModes(authentication: Authentication): Rule {
        val username = SecurityUtil.getUsername(authentication)
        val cacheName = getCacheName(username)
        if (!redisTemplate.hasKey(cacheName)) {
            val userDetail = fetchJwtUserDetail(username)
            logger.info("fetch user(${userDetail.username})'s authorities")
        }
        return redisTemplate.opsForValue().get(cacheName) ?: throw PermissionException("NONE AUTHORITIES")
    }

    override fun clear(cacheName: String) {
        redisTemplate.delete(cacheName)
    }

    private fun getCacheName(username: String): String = "$APPLICATION_NAME:users:$username:role-rule"

    private fun convert(authorities: List<GrantedAuthorityImpl>): Rule {
        val expr = JsonNodeFactory.instance.objectNode()
        authorities.map {
            // TODO 想一下为什么authority会包含双引号
            val permission = it.authority.replace("\"", "").split(" ")
            permission[0] to permission[1]
        }.groupBy { it.first }.forEach { (httpMethod, point) ->
            expr.with(HTTP_METHOD).putObject(httpMethod).putArray(REQUEST_URL).apply {
                point.forEach { this.add(it.second) }
            }
        }
        return Rule(0, expr)
    }

    private fun store(username: String, authorities: Rule) {
        val cacheName = getCacheName(username)
        this.clear(cacheName)
        redisTemplate.opsForValue().set(cacheName, authorities, DEFAULT_CACHE_EXPIRE_TIME, TimeUnit.SECONDS)
    }
}