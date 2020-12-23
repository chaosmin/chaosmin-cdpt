package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.Partner
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.PartnerEntity
import tech.chaosmin.framework.domain.request.PartnerReq
import tech.chaosmin.framework.domain.response.PartnerResp
import tech.chaosmin.framework.handler.ModifyPartnerHandler
import tech.chaosmin.framework.handler.convert.PartnerConvert
import tech.chaosmin.framework.handler.logic.PartnerQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.PartnerShareService
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class PartnerShareProvider(
    private val partnerQueryLogic: PartnerQueryLogic,
    private val modifyPartnerHandler: ModifyPartnerHandler
) : PartnerShareService {
    override fun selectById(id: Long): RestResult<PartnerResp?> {
        val partner = partnerQueryLogic.get(id)
        return if (partner == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(PartnerConvert.INSTANCE.convert2Resp(partner))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<PartnerResp>> {
        val queryCondition = RequestUtil.getQueryCondition<Partner>(request)
        val page = partnerQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(PartnerConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: PartnerReq): RestResult<PartnerResp> {
        val partner = PartnerConvert.INSTANCE.convert2Entity(req)
        partner.save()
        return RestResultExt.execute(modifyPartnerHandler, partner, PartnerConvert::class.java)
    }

    override fun update(id: Long, req: PartnerReq): RestResult<PartnerResp> {
        val partner = PartnerConvert.INSTANCE.convert2Entity(req)
        partner.update(id)
        return RestResultExt.execute(modifyPartnerHandler, partner, PartnerConvert::class.java)
    }

    override fun delete(id: Long): RestResult<PartnerResp> {
        val partner = PartnerEntity(id)
        partner.remove()
        return RestResultExt.execute(modifyPartnerHandler, partner, PartnerConvert::class.java)
    }
}