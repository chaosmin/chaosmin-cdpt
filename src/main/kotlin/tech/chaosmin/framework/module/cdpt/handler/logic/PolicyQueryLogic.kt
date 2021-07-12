package tech.chaosmin.framework.module.cdpt.handler.logic

import cn.hutool.core.date.DateUtil
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.base.enums.PolicyKhsEnum
import tech.chaosmin.framework.base.enums.PolicyStatusEnum
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyExt
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyKhsEntity
import tech.chaosmin.framework.module.cdpt.entity.response.DashboardResp
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyHolderMapper
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyInsurantMapper
import tech.chaosmin.framework.module.cdpt.helper.mapper.PolicyMapper
import tech.chaosmin.framework.module.cdpt.service.inner.*
import tech.chaosmin.framework.module.mgmt.handler.logic.UserQueryLogic
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*

/**
 * 保单数据查询逻辑 <p>
 * <p>
 * @author Romani min
 * @since 2020/12/17 15:28
 */
@Component
class PolicyQueryLogic(
    private val policyService: PolicyService,
    private val policyHolderService: PolicyHolderService,
    private val policyInsurantService: PolicyInsurantService,
    private val policyKhsService: PolicyKhsService,
    private val orderTempService: OrderTempService,
    private val goodsPlanQueryLogic: GoodsPlanQueryLogic,
    private val userQueryLogic: UserQueryLogic
) : BaseQueryLogic<PolicyEntity, PolicyExt> {
    override fun get(id: Long): PolicyEntity? {
        val policy = policyService.getById(id)
        return PolicyMapper.INSTANCE.convert2Entity(policy)?.apply {
            // 保单投保人信息
            holder = PolicyHolderMapper.INSTANCE.convert2Entity(policyHolderService.listByPolicyId(id).firstOrNull())
            // 保单被保人信息
            insuredList = PolicyInsurantMapper.INSTANCE.convert2Entity(policyInsurantService.listByPolicyId(id))
            // 保单产品信息
            goodsPlan = goodsPlanQueryLogic.get(policy.goodsPlanId!!)
        }
    }

    override fun page(cond: PageQuery<PolicyExt>): IPage<PolicyEntity> {
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            val subordinate = userQueryLogic.findSubordinate().mapNotNull { it.loginName }.toMutableList()
            subordinate.add(SecurityUtil.getUsername())
            queryWrapper = queryWrapper.`in`("policy.creator", subordinate)
        }
        val page = policyService.pageExt(cond.page, queryWrapper)
        return page.convert(PolicyMapper.INSTANCE::convert2Entity)
    }

    fun queryDetail(orderNo: String): String {
        val list = orderTempService.listByOrderNo(orderNo)
        val orderTemp = list.sortedByDescending { it.createTime }.firstOrNull()
        return orderTemp?.param ?: "{}"
    }

    /**
     * 查询保单可回溯信息
     */
    fun queryKhs(id: Long): PolicyKhsEntity {
        val policy = this.page(PageQuery.eqQuery("policy.id", id)).records.firstOrNull() ?: return PolicyKhsEntity()
        val list = policyKhsService.listByOrderNo(policy.orderNo!!)
        val policyKhsEntity = PolicyKhsEntity().apply {
            this.leavePageTime = DateUtil.formatDateTime(policy.createTime)
            this.orderNo = policy.orderNo
            this.policyNo = policy.policyNo
            this.holderName = policy.holder?.name
            this.issuerName = policy.creator
        }
        list.firstOrNull { PolicyKhsEnum.ENTER_PAGE.getCode() == it.khsType }?.run {
            policyKhsEntity.enterPageTime = DateUtil.formatDateTime(this.fileTime)
        }
        list.firstOrNull { PolicyKhsEnum.POLICY_NOTICE.getCode() == it.khsType }?.run {
            policyKhsEntity.readTime = DateUtil.formatDateTime(this.fileTime)
            policyKhsEntity.readPicUrl = this.resourceUrl
        }
        list.firstOrNull { PolicyKhsEnum.INSU_CLAUSES.getCode() == it.khsType }?.run {
            policyKhsEntity.confirmTime = DateUtil.formatDateTime(this.fileTime)
            policyKhsEntity.confirmPicUrl = this.resourceUrl
        }
        list.firstOrNull { PolicyKhsEnum.INSU_CONFIRM.getCode() == it.khsType }?.run {
            policyKhsEntity.issueTime = DateUtil.formatDateTime(this.fileTime)
            policyKhsEntity.issuePicUrl = this.resourceUrl
        }
        return policyKhsEntity
    }

    fun dashboardData(): DashboardResp {
        val startOfThisWeek = DateUtil.beginOfWeek(Date()).time
        val ew = Wrappers.query<PolicyExt>().eq("policy.status", PolicyStatusEnum.SUCCESS.getCode())
        // 全量数据
        val list = policyService.listExt(ew)
        val lineData = list.groupBy { DateUtil.beginOfDay(it.createTime).time }.filter { it.key >= startOfThisWeek }
        return DashboardResp().apply {
            this.policies.apply {
                value = list.size.toDouble()
                actualData = (0..6).mapNotNull { lineData[startOfThisWeek + it * 24 * 60 * 60 * 1000]?.size?.toDouble() ?: 0.0 }
            }
            this.insureds.apply {
                value = list.sumByDouble { it.insuredSize?.toDouble() ?: 0.0 }
                actualData = (0..6).mapNotNull {
                    lineData[startOfThisWeek + it * 24 * 60 * 60 * 1000]?.sumByDouble { i -> i.insuredSize?.toDouble() ?: 0.0 } ?: 0.0
                }
            }
            this.premium.apply {
                value = list.sumByDouble { it.actualPremium ?: 0.0 }
                actualData =
                    (0..6).mapNotNull { lineData[startOfThisWeek + it * 24 * 60 * 60 * 1000]?.sumByDouble { i -> i.actualPremium ?: 0.0 } ?: 0.0 }
            }
            this.issuers.value = list.distinctBy { it.creator }.size.toDouble()
        }
    }

}