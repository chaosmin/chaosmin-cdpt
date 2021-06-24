package tech.chaosmin.framework.module.cdpt.handler.logic

import cn.hutool.core.date.DateUtil
import com.baomidou.mybatisplus.core.metadata.IPage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.domain.enums.PolicyKhsEnum
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyHolderEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyKhsEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyHolderMapper
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyInsurantMapper
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyMapper
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyHolderService
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyInsurantService
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyKhsService
import tech.chaosmin.framework.module.cdpt.service.inner.PolicyService
import tech.chaosmin.framework.module.mgmt.handler.SubordinateHandler
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * 保单数据查询逻辑 <p>
 * <p>
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class PolicyQueryLogic(
    private val subordinateHandler: SubordinateHandler,
    private val policyService: PolicyService,
    private val policyKhsService: PolicyKhsService,
    private val policyHolderService: PolicyHolderService,
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
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            val username = SecurityUtil.getUsername()
            val subordinate = subordinateHandler.findAll(username)
            queryWrapper = queryWrapper.`in`("policy.creator", subordinate)
        }
        val page = policyService.page(cond.page, queryWrapper)
        logger.debug("Page(${JsonUtil.encode(cond)}) => ${JsonUtil.encode(page)}")
        val result = page.convert(PolicyMapper.INSTANCE::convert2Entity)
        // 补充被保人列表
        result.convert {
            it?.apply {
                this.holder = queryHolder(it.id!!)[0]
                this.insuredList = queryInsurant(it.id!!)
            }
        }
        logger.debug("Convert2Entity => ${JsonUtil.encode(result)}")
        return result
    }

    private fun queryHolder(id: Long): List<PolicyHolderEntity> {
        return policyHolderService.listByPolicyId(id).mapNotNull {
            PolicyHolderMapper.INSTANCE.convert2Entity(it)
        }
    }

    private fun queryInsurant(id: Long): List<PolicyInsurantEntity> {
        return policyInsurantService.listByPolicyId(id).mapNotNull {
            PolicyInsurantMapper.INSTANCE.convert2Entity(it)
        }
    }

    fun queryKhs(id: Long): PolicyKhsEntity {
        val policy = this.page(PageQuery.eqQuery("policy.id", id)).records.firstOrNull() ?: return PolicyKhsEntity()
        val list = policyKhsService.listByPolicyId(id)
        val policyKhsEntity = PolicyKhsEntity().apply {
            this.orderNo = policy.orderNo
            this.policyNo = policy.policyNo
            this.holderName = policy.holder?.name
            this.issuerName = policy.creator
        }
        list.firstOrNull { PolicyKhsEnum.POLICY_NOTICE.getCode() == it.khsType }?.run {
            policyKhsEntity.readTime = DateUtil.formatDateTime(this.fileTime)
            policyKhsEntity.readPicUrl = this.resourceUrl
        }
        list.firstOrNull { PolicyKhsEnum.INSU_CONFIRM.getCode() == it.khsType }?.run {
            policyKhsEntity.issueTime = DateUtil.formatDateTime(this.fileTime)
            policyKhsEntity.issuePicUrl = this.resourceUrl
        }
        return policyKhsEntity
    }
}