package tech.chaosmin.framework.module.cdpt.service.external

import tech.chaosmin.framework.module.cdpt.entity.channel.BaseChannelReq
import tech.chaosmin.framework.module.cdpt.entity.channel.BaseChannelResp

/**
 * @author Romani min
 * @since 2021/6/16 13:43
 */
interface BasePolicyService<D : BaseChannelReq, T : BaseChannelResp> {
    /**
     * 保费试算处理逻辑
     * @param request 请求参数
     * @return 响应参数
     */
    fun calculatePremium(request: D): T

    /**
     * 核保处理逻辑
     * @param request 请求参数
     * @return 响应参数
     */
    fun underwriting(request: D): T

    /**
     * 保单取消, 无退费
     * @param request 请求参数
     * @return 响应参数
     */
    fun cancelPolicy(request: D): T

    /**
     * 保单退保, 含退费
     * @param request 请求参数
     * @return 响应参数
     */
    fun refundPolicy(request: D): T
}