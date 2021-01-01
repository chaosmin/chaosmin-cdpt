package tech.chaosmin.framework.module.mgmt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Authority
import tech.chaosmin.framework.module.mgmt.entity.AuthorityEntity
import tech.chaosmin.framework.module.mgmt.entity.response.AuthorityTreeNodeResp
import tech.chaosmin.framework.module.mgmt.helper.mapper.AuthorityMapper
import tech.chaosmin.framework.module.mgmt.service.AuthorityService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class AuthorityQueryLogic(private val authorityService: AuthorityService) : BaseQueryLogic<AuthorityEntity, Authority> {

    override fun get(id: Long): AuthorityEntity? {
        val authority = authorityService.getById(id)
        return if (authority == null) null
        else AuthorityMapper.INSTANCE.convert2Entity(authority)
    }

    override fun page(cond: PageQuery<Authority>): IPage<AuthorityEntity> {
        val page = authorityService.page(cond.page, cond.wrapper)
        return page.convert(AuthorityMapper.INSTANCE::convert2Entity)
    }

    fun tree(): List<AuthorityTreeNodeResp> {
        val list = authorityService.list()
        return list.filter { it.parentId == null }.map { AuthorityTreeNodeResp(it, list) }
    }
}