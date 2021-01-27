package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyMapper
import tech.chaosmin.framework.module.cdpt.service.PolicyService

/**
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class PolicyQueryLogic(private val policyService: PolicyService) : BaseQueryLogic<PolicyEntity, Policy> {

    override fun get(id: Long): PolicyEntity? {
        val policy = policyService.getById(id)
        return PolicyMapper.INSTANCE.convert2Entity(policy)
    }

    override fun page(cond: PageQuery<Policy>): IPage<PolicyEntity?> {
        val page = policyService.page(cond.page, cond.wrapper)
        return page.convert(PolicyMapper.INSTANCE::convert2Entity)
    }
}