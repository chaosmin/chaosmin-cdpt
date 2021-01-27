package tech.chaosmin.framework.module.cdpt.api.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.api.OrderShareService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Order
import tech.chaosmin.framework.module.cdpt.entity.request.OrderReq
import tech.chaosmin.framework.module.cdpt.entity.response.OrderResp
import tech.chaosmin.framework.module.cdpt.handler.logic.OrderQueryLogic
import tech.chaosmin.framework.module.cdpt.helper.convert.OrderConvert
import tech.chaosmin.framework.utils.RequestUtil
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class OrderShareProvider(
    private val orderQueryLogic: OrderQueryLogic
) : OrderShareService {
    override fun selectById(id: Long): RestResult<OrderResp?> {
        val order = orderQueryLogic.get(id)
        return if (order == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(OrderConvert.INSTANCE.convert2Resp(order))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<OrderResp?>> {
        val queryCondition = RequestUtil.getQueryCondition<Order>(request)
        val page = orderQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(OrderConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: OrderReq): RestResult<OrderResp> {
        throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION.code)
    }

    override fun update(id: Long, req: OrderReq): RestResult<OrderResp> {
        throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION.code)
    }

    override fun delete(id: Long): RestResult<OrderResp> {
        throw FrameworkException(ErrorCodeEnum.NOT_SUPPORTED_FUNCTION.code)
    }
}