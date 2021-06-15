package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.BasicStatusEnum
import tech.chaosmin.framework.base.enums.BizNoTypeEnum
import tech.chaosmin.framework.base.enums.YesNoEnum
import tech.chaosmin.framework.module.cdpt.api.InsureShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanLiability
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanRateTable
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.GoodsPlanExt
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsCategoryResp
import tech.chaosmin.framework.module.cdpt.entity.response.GoodsInsuredResp
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyResp
import tech.chaosmin.framework.module.cdpt.handler.IssuePolicyHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.GoodsPlanQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.PlanLiabilityConvert
import tech.chaosmin.framework.module.cdpt.helper.convert.PlanRateTableConvert
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanLiabilityMapper
import tech.chaosmin.framework.module.cdpt.helper.mapper.PlanRateTableMapper
import tech.chaosmin.framework.module.cdpt.service.PlanLiabilityService
import tech.chaosmin.framework.module.cdpt.service.PlanRateTableService
import tech.chaosmin.framework.module.cdpt.service.ProductExternalService
import tech.chaosmin.framework.utils.BizNoUtil
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.utils.SecurityUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2021/1/15 16:36
 */
@RestController
open class InsureShareProvider(
    private val goodsPlanQueryLogic: GoodsPlanQueryLogic,
    private val planLiabilityService: PlanLiabilityService,
    private val planRateTableService: PlanRateTableService,
    private val productExternalService: ProductExternalService,
    private val issuePolicyHandler: IssuePolicyHandler
) : InsureShareService {
    private val logger = LoggerFactory.getLogger(InsureShareService::class.java)

    override fun getGoodsCategories(): RestResult<List<GoodsCategoryResp>> {
        val userId = SecurityUtil.getUserId()
        val goodsCategories = goodsPlanQueryLogic.getGoodsCategories(userId)
        val result = mutableListOf<GoodsCategoryResp>()
        goodsCategories.groupBy { it.categoryName }.forEach { (c, l) ->
            result.add(GoodsCategoryResp(c!!, c).apply {
                this.children = l.map { g -> GoodsCategoryResp(g.id.toString(), g.categorySubName!!) }
            })
        }
        return RestResultExt.successRestResult(result)
    }

    override fun getGoods(request: HttpServletRequest): RestResult<List<GoodsInsuredResp>> {
        val queryCondition = RequestUtil.getQueryCondition<GoodsPlanExt>(request)
        // 扩展至500条(所有符合条件的数据)
        queryCondition.page.size = 500
        queryCondition.wrapper
            .eq("goods_plan.status", BasicStatusEnum.ENABLED.getCode())
            .eq("goods_plan.is_deleted", YesNoEnum.NO.getCode())
            .orderByAsc("product_plan.id")
        // 如果是非管理员用户仅能查看自己授权的产品信息
        if (SecurityUtil.getUserDetails()?.isAdmin != true) {
            queryCondition.wrapper.eq("user_id", SecurityUtil.getUserId())
        }
        val goods = goodsPlanQueryLogic.page(queryCondition).records
        val result = goods.map {
            GoodsInsuredResp().apply {
                this.productPlanId = it?.productPlanId
                this.partnerCode = it?.partnerCode
                this.partnerName = it?.partnerName
                this.productName = it?.productName
                this.productPlanName = it?.productPlanName
                this.primaryCoverage = it?.primaryCoverage
                this.waitingDays = it?.waitingDays
                this.comsRatio = it?.comsRatio
                this.goodsLiabilities = planLiabilityService.list(Wrappers.query<PlanLiability>().eq("product_plan_id", it?.productPlanId))
                    .mapNotNull { PlanLiabilityConvert.INSTANCE.convert2Resp(PlanLiabilityMapper.INSTANCE.convert2Entity(it)) }
                this.goodsRateTable = planRateTableService.list(Wrappers.query<PlanRateTable>().eq("product_plan_id", it?.productPlanId))
                    .mapNotNull { PlanRateTableConvert.INSTANCE.convert2Resp(PlanRateTableMapper.INSTANCE.convert2Entity(it)) }
                val ex = productExternalService.getByProductId(it?.productId!!)
                this.insuranceNotice = ex.insuranceNotice
                this.productExternal = ex.externalText
            }
        }
        return RestResultExt.successRestResult(result)
    }

    override fun getBizNo(): RestResult<String> {
        val bizNo = BizNoUtil.nextBizNo(BizNoTypeEnum.DATETIME, 18, "O")
        return RestResultExt.successRestResult(bizNo)
    }

    override fun insurance(req: PolicyIssueReq): RestResult<PolicyResp> {
        return issuePolicyHandler.operate(req)
    }
}