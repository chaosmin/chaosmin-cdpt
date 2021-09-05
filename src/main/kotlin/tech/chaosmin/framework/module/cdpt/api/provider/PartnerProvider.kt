/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.AbstractAPI
import tech.chaosmin.framework.module.cdpt.api.PartnerAPI
import tech.chaosmin.framework.module.cdpt.api.convert.PartnerConvert
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Partner
import tech.chaosmin.framework.module.cdpt.entity.PartnerEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PartnerReq
import tech.chaosmin.framework.module.cdpt.entity.response.PartnerResp
import tech.chaosmin.framework.module.cdpt.logic.handler.PartnerModifyHandler
import tech.chaosmin.framework.module.cdpt.logic.interrogator.PartnerInterrogator

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class PartnerProvider(
    private val partnerInterrogator: PartnerInterrogator,
    private val partnerModifyHandler: PartnerModifyHandler
) : AbstractAPI<Partner, PartnerEntity, PartnerReq, PartnerResp>(
    PartnerConvert.INSTANCE, partnerInterrogator, partnerModifyHandler
), PartnerAPI