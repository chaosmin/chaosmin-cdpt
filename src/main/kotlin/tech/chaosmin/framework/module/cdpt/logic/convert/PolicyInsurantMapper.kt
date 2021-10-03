/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * PolicyInsurantMapper.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.module.cdpt.logic.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.IMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyInsurant
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity

@Mapper(uses = [KeyValueEnumMapper::class])
interface PolicyInsurantMapper : IMapper<PolicyInsurantEntity, PolicyInsurant> {
    companion object {
        val INSTANCE: PolicyInsurantMapper = Mappers.getMapper(PolicyInsurantMapper::class.java)
    }
}