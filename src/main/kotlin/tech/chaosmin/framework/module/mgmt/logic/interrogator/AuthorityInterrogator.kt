/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * AuthorityInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.mgmt.logic.interrogator

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.Authority
import tech.chaosmin.framework.module.mgmt.entity.AuthorityEntity
import tech.chaosmin.framework.module.mgmt.entity.response.AuthorityTreeNodeResp
import tech.chaosmin.framework.module.mgmt.helper.mapper.AuthorityMapper
import tech.chaosmin.framework.module.mgmt.service.AuthorityService

/**
 * @author Romani min
 * @since 2021/9/4 23:46
 */
@Component
class AuthorityInterrogator(private val authorityService: AuthorityService) : Interrogator<AuthorityEntity, Authority> {
    override fun getOne(id: Long): AuthorityEntity? {
        val authority = authorityService.getById(id)
        return AuthorityMapper.INSTANCE.convert2Entity(authority)
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