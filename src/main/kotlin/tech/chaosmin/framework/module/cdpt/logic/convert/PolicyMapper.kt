/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PolicyMapper.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.IMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyEx
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity

@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyMapper : IMapper<PolicyEntity, Policy> {
    companion object {
        val INSTANCE: PolicyMapper = Mappers.getMapper(PolicyMapper::class.java)
    }

    @Mappings(
        Mapping(source = "holderName", target = "holder.name"),
        Mapping(source = "partnerName", target = "goodsPlan.partnerName"),
        Mapping(target = "modifyType", ignore = true)
    )
    fun exToEn(source: PolicyEx?): PolicyEntity?

    fun exToEn(source: List<PolicyEx>?): List<PolicyEntity>?
}