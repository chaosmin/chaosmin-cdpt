/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PolicyInterrogator.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.interrogator

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.Interrogator
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyEx
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyHolderService
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyInsurantService
import tech.chaosmin.framework.module.cdpt.domain.service.PolicyService
import tech.chaosmin.framework.module.cdpt.entity.OrderTraceEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.logic.convert.PolicyHolderMapper
import tech.chaosmin.framework.module.cdpt.logic.convert.PolicyInsurantMapper
import tech.chaosmin.framework.module.cdpt.logic.convert.PolicyMapper
import tech.chaosmin.framework.module.mgmt.logic.interrogator.UserInterrogator
import tech.chaosmin.framework.utils.SecurityUtil

/**
 * @author Romani min
 * @since 2021/9/4 22:06
 */
@Component
class PolicyInterrogator(
    private val userInterrogator: UserInterrogator,
    private val policyService: PolicyService,
    private val policyHolderService: PolicyHolderService,
    private val policyInsurantService: PolicyInsurantService,
    private val goodsPlanInterrogator: GoodsPlanInterrogator
) : Interrogator<PolicyEntity, PolicyEx> {
    override fun getOne(id: Long): PolicyEntity? {
        val policy = policyService.getById(id)
        val en = PolicyMapper.INSTANCE.toEn(policy)
        return en?.apply {
            // 保单投保人信息
            this.holder = PolicyHolderMapper.INSTANCE.toEn(policyHolderService.listByPolicyId(id).firstOrNull())
            // 保单被保人信息
            this.insuredList = PolicyInsurantMapper.INSTANCE.toEn(policyInsurantService.listByPolicyId(id))
            // 保单产品信息
            this.goodsPlan = goodsPlanInterrogator.getOne(policy.goodsPlanId!!)
        } ?: en
    }

    fun getOne(ew: Wrapper<Policy>): PolicyEntity? {
        val en = PolicyMapper.INSTANCE.toEn(policyService.getOne(ew))
        return en?.apply {
            // 保单投保人信息
            this.holder = PolicyHolderMapper.INSTANCE.toEn(policyHolderService.listByPolicyId(en.id!!).firstOrNull())
            // 保单被保人信息
            this.insuredList = PolicyInsurantMapper.INSTANCE.toEn(policyInsurantService.listByPolicyId(en.id!!))
            // 保单产品信息
            this.goodsPlan = goodsPlanInterrogator.getOne(en.goodsPlanId!!)
        } ?: en
    }

    override fun page(cond: PageQuery<PolicyEx>): IPage<PolicyEntity> {
        var queryWrapper = cond.wrapper
        if (!SecurityUtil.getUserDetails().isAdmin) {
            val subordinate = userInterrogator.findSubordinate().mapNotNull { it.id }.toMutableList()
            subordinate.add(SecurityUtil.getUserId())
            queryWrapper = queryWrapper.`in`("policy.user_id", subordinate)
        }
        val page = policyService.pageExt(cond.page, queryWrapper)
        return page.convert(PolicyMapper.INSTANCE::exToEn)
    }

    fun queryDetail(orderNo: String): String {
//        val list = orderTempService.listByOrderNo(orderNo)
//        val orderTemp = list.sortedByDescending { it.createTime }.firstOrNull()
//        return orderTemp?.param ?: "{}"
        TODO("Not yet implemented")
    }

    /**
     * 查询保单可回溯信息
     */
    fun queryKhs(id: Long): OrderTraceEntity {
//        val policy = this.page(PageQuery.eqQuery("policy.id", id)).records.firstOrNull() ?: return PolicyKhsEntity()
//        val list = policyKhsService.listByOrderNo(policy.orderNo!!)
//        val policyKhsEntity = PolicyKhsEntity().apply {
//            this.leavePageTime = DateUtil.formatDateTime(policy.createTime)
//            this.orderNo = policy.orderNo
//            this.policyNo = policy.policyNo
//            this.holderName = policy.holder?.name
//            this.issuerName = policy.creator
//        }
//        list.firstOrNull { PolicyKhsEnum.ENTER_PAGE.getCode() == it.khsType }?.run {
//            policyKhsEntity.enterPageTime = DateUtil.formatDateTime(this.fileTime)
//        }
//        list.firstOrNull { PolicyKhsEnum.POLICY_NOTICE.getCode() == it.khsType }?.run {
//            policyKhsEntity.readTime = DateUtil.formatDateTime(this.fileTime)
//            policyKhsEntity.readPicUrl = this.resourceUrl
//        }
//        list.firstOrNull { PolicyKhsEnum.INSU_CLAUSES.getCode() == it.khsType }?.run {
//            policyKhsEntity.confirmTime = DateUtil.formatDateTime(this.fileTime)
//            policyKhsEntity.confirmPicUrl = this.resourceUrl
//        }
//        list.firstOrNull { PolicyKhsEnum.INSU_CONFIRM.getCode() == it.khsType }?.run {
//            policyKhsEntity.issueTime = DateUtil.formatDateTime(this.fileTime)
//            policyKhsEntity.issuePicUrl = this.resourceUrl
//        }
//        return policyKhsEntity
        TODO("Not yet implemented")
    }
}