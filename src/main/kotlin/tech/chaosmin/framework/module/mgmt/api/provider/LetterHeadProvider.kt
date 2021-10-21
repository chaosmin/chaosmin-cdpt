/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * LetterHeadProvider.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.mgmt.api.provider

import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.mgmt.api.LetterHeadAPI
import tech.chaosmin.framework.module.mgmt.entity.response.LetterHeadResp
import tech.chaosmin.framework.module.mgmt.helper.convert.LetterHeadConvert
import tech.chaosmin.framework.module.mgmt.helper.mapper.LetterHeadMapper
import tech.chaosmin.framework.module.mgmt.service.LetterHeadService

/**
 * @author Romani min
 * @since 2021/10/21 13:44
 */
@RestController
open class LetterHeadProvider(private val letterHeadService: LetterHeadService) : LetterHeadAPI {
    override fun getByUser(userId: Long): RestResult<List<LetterHeadResp>> {
        val letterHeads = letterHeadService.fetchByUserId(userId)
        if (letterHeads.isEmpty()) {
            return RestResultExt.successRestResult()
        }

        val list = LetterHeadConvert.INSTANCE.convert2Resp(LetterHeadMapper.INSTANCE.convert2Entity(letterHeads.toList())!!)
        return RestResultExt.successRestResult(list)
    }
}