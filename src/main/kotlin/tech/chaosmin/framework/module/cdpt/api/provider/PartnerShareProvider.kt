package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.cdpt.api.PartnerShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Partner
import tech.chaosmin.framework.module.cdpt.entity.PartnerEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PartnerReq
import tech.chaosmin.framework.module.cdpt.entity.response.PartnerResp
import tech.chaosmin.framework.module.cdpt.handler.ModifyPartnerHandler
import tech.chaosmin.framework.module.cdpt.handler.logic.PartnerQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.PartnerConvert
import tech.chaosmin.framework.utils.RequestUtil
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
    override fun selectById(id: Long): RestResult<PartnerResp> {
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
        val partner = PartnerConvert.INSTANCE.convert2Entity(req).save()
        val result = modifyPartnerHandler.operate(partner)
        return RestResultExt.mapper<PartnerResp>(result).convert {
            PartnerConvert.INSTANCE.convert2Resp(result.data ?: PartnerEntity())
        }
    }

    override fun update(id: Long, req: PartnerReq): RestResult<PartnerResp> {
        val partner = PartnerConvert.INSTANCE.convert2Entity(req).update(id)
        val result = modifyPartnerHandler.operate(partner)
        return RestResultExt.mapper<PartnerResp>(result).convert {
            PartnerConvert.INSTANCE.convert2Resp(result.data ?: PartnerEntity())
        }
    }

    override fun delete(id: Long): RestResult<PartnerResp> {
        val partner = PartnerEntity(id).remove()
        val result = modifyPartnerHandler.operate(partner)
        return RestResultExt.mapper<PartnerResp>(result).convert {
            PartnerConvert.INSTANCE.convert2Resp(result.data ?: PartnerEntity())
        }
    }
}