package tech.chaosmin.framework.module.cdpt.handler

import cn.hutool.core.date.DateUtil
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.base.enums.BizNoTypeEnum
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyInsuredReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.utils.BizNoUtil
import java.util.*
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/6/21 19:41
 */
internal class IssuePolicyHandlerBootTest : BaseTestMain() {
    @Resource
    lateinit var issuePolicyHandler: IssuePolicyHandler

    @Test
    fun operate() {
        val policyIssueReq = PolicyIssueReq()
        policyIssueReq.orderNo = BizNoUtil.nextBizNo(BizNoTypeEnum.DATETIME, 20, "BL")
        policyIssueReq.startTime = DateUtil.parse("2021-07-01 00:00:00", "yyyy-MM-dd hh:mm:ss")
        policyIssueReq.endTime = DateUtil.parse("2021-07-30 23:59:59", "yyyy-MM-dd hh:mm:ss")
        policyIssueReq.days = 30
        policyIssueReq.unitPremium = 75.0
        policyIssueReq.totalPremium = 75.0
        policyIssueReq.actualPremium = 75.0
        policyIssueReq.policyHolderName = "堡垒科技"
        policyIssueReq.policyHolderCerti = "340501198008040073"
        policyIssueReq.insuredList = Collections.singletonList(PolicyInsuredReq().apply {
            this.name = "柳锦"
            this.certiNo = "110101199706219097"
            this.certiType = "身份证"
            this.gender = "男"
            this.mobile = "17601348927"
        })
        issuePolicyHandler.operate(policyIssueReq)
    }
}