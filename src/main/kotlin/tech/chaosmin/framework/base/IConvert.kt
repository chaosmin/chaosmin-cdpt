/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * IConvert.java
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.base

import org.mapstruct.Mapping
import org.mapstruct.Mappings

/**
 * @author Romani min
 * @since 2021/9/4 22:57
 */
interface IConvert<E : BaseEntity<E>, RE : BaseReq, RQ : BaseResp> {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "modifyType", ignore = true),
        Mapping(target = "createTime", ignore = true),
        Mapping(target = "creator", ignore = true),
        Mapping(target = "updateTime", ignore = true),
        Mapping(target = "updater", ignore = true),
        Mapping(target = "deleted", ignore = true)
    )
    fun toEn(req: RE): E

    fun toResp(entity: E): RQ

    fun toResp(entityList: List<E>): List<RQ>
}