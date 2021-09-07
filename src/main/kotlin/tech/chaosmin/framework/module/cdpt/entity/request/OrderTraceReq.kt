package tech.chaosmin.framework.module.cdpt.entity.request

import io.swagger.annotations.ApiModel
import tech.chaosmin.framework.base.BaseReq
import java.util.*

/**
 * @author Romani min
 * @since 2021/6/9 10:04
 */
@ApiModel("保单可回溯请求参数")
class OrderTraceReq : BaseReq() {
    var type: String? = null
    var time: Date? = null
    var url: String? = null
}