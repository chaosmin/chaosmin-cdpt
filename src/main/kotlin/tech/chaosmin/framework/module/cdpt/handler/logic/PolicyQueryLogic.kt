package tech.chaosmin.framework.module.cdpt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyInsurantMapper
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyMapper
import tech.chaosmin.framework.module.cdpt.service.PolicyInsurantService
import tech.chaosmin.framework.module.cdpt.service.PolicyService
import tech.chaosmin.framework.utils.JsonUtil

/**
 * 保单数据查询逻辑 <br/>
 * <br/>
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class PolicyQueryLogic(
    private val policyService: PolicyService,
    private val policyInsurantService: PolicyInsurantService
) : BaseQueryLogic<PolicyEntity, Policy> {
    private val logger = LoggerFactory.getLogger(PolicyQueryLogic::class.java)

    override fun get(id: Long): PolicyEntity? {
        val policy = policyService.getById(id)
        logger.debug("GetById($id) => ${JsonUtil.encode(policy)}")
        val policyEntity = PolicyMapper.INSTANCE.convert2Entity(policy)
        logger.debug("Convert2Entity => ${JsonUtil.encode(policyEntity)}")
        return policyEntity
    }

    override fun page(cond: PageQuery<Policy>): IPage<PolicyEntity?> {
        val page = policyService.page(cond.page, cond.wrapper)
        logger.debug("Page(${JsonUtil.encode(cond)}) => ${JsonUtil.encode(page)}")
        val result = page.convert(PolicyMapper.INSTANCE::convert2Entity)
        // 补充被保人列表
        result.convert {
            it.apply {
                this?.insuredList = policyInsurantService.listByPolicyId(it?.id!!).mapNotNull { p ->
                    PolicyInsurantMapper.INSTANCE.convert2Entity(p)
                }
            }
        }
        logger.debug("Convert2Entity => ${JsonUtil.encode(result)}")
        return result
    }
}