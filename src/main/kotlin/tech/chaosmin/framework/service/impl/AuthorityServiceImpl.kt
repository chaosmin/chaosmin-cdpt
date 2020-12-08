package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.dao.AuthorityDAO
import tech.chaosmin.framework.dao.dataobject.Authority
import tech.chaosmin.framework.domain.const.SystemConst.AUTHORITY_CACHE_EXPIRE_TIME
import tech.chaosmin.framework.domain.const.SystemConst.CACHE_NAMESPACE_AUTHORITY
import tech.chaosmin.framework.service.AuthorityService
import tech.chaosmin.framework.utils.JsonUtil
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Service
open class AuthorityServiceImpl(private val stringRedisTemplate: StringRedisTemplate) :
    ServiceImpl<AuthorityDAO, Authority>(), AuthorityService {

    companion object {
        private const val ttl = AUTHORITY_CACHE_EXPIRE_TIME
        private val timeUnit = TimeUnit.SECONDS

    }

    @PostConstruct
    fun readAuthoritiesToCache() {
        synchronized(this) {
            baseMapper.selectList(Wrappers.emptyWrapper()).forEach { authority ->
                val cacheKey = "$CACHE_NAMESPACE_AUTHORITY${authority.httpMethod} ${authority.url}"
                val cache = JsonUtil.encode(authority)
                stringRedisTemplate.opsForValue().set(cacheKey, cache, ttl, timeUnit)
            }
        }
    }

    override fun findAuthorities(roleIds: Set<Long>): Set<Authority> {
        return if (roleIds.isEmpty()) emptySet()
        else baseMapper.findAuthorities(roleIds)
    }

    override fun findAuthorities(authority: String): Authority? {
        val cacheKey = "$CACHE_NAMESPACE_AUTHORITY$authority"
        var cacheJson: String? = null
        if (stringRedisTemplate.hasKey(cacheKey)) {
            cacheJson = stringRedisTemplate.opsForValue().get(cacheKey)
        }
        if (cacheJson.isNullOrBlank()) {
            synchronized(this) {
                val method = authority.split(" ")[0]
                val url = authority.split(" ")[1]
                return baseMapper.selectOne(Wrappers.query(Authority(method, url)))?.also {
                    stringRedisTemplate.opsForValue().set(cacheKey, JsonUtil.encode(authority), ttl, timeUnit)
                }
            }
        }
        return JsonUtil.decode(cacheJson, Authority::class.java)
    }

    @Transactional
    override fun updateAuthorities(roleId: Long?, authorityIds: List<Long>?): Set<Authority> {
        return if (roleId != null && authorityIds != null && authorityIds.isNotEmpty()) {
            val assigned = baseMapper.findAuthorities(setOf(roleId)).mapNotNull { it.id }
            (authorityIds - assigned).run {
                if (this.isNotEmpty()) baseMapper.addAuthorities(roleId, this)
            }
            (assigned - authorityIds).run {
                if (this.isNotEmpty()) baseMapper.removeAuthorities(roleId, this)
            }
            baseMapper.selectBatchIds(authorityIds).toSet()
        } else emptySet()
    }

    @Transactional
    override fun clearAuthorities(roleId: Long) {
        baseMapper.clearAuthorities(roleId)
    }
}