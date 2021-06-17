package tech.chaosmin.framework.module.cdpt.service.external

import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.response.DDResp

/**
 * @author Romani min
 * @since 2021/6/16 13:39
 */
interface DadiPolicyService : BasePolicyService<DDReq<*>, DDResp<*>>