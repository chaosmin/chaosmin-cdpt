/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.api.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.IConvert
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyInsurantReq
import tech.chaosmin.framework.module.cdpt.entity.response.PolicyInsurantResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface PolicyInsurantConvert : IConvert<PolicyInsurantEntity, PolicyInsurantReq, PolicyInsurantResp> {
    companion object {
        val INSTANCE: PolicyInsurantConvert = Mappers.getMapper(PolicyInsurantConvert::class.java)
    }
}