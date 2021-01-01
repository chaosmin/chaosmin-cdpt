package tech.chaosmin.framework.base

import org.mapstruct.Mapping
import org.mapstruct.Mappings

/**
 * @author Romani min
 * @since 2020/12/23 16:31
 */
interface BaseConvert<E : BaseEntity, RE : BaseReq, RQ : BaseResp> {
    fun convert2Resp(source: E): RQ

    fun convert2Resp(source: List<E>): List<RQ>

    @Mappings(
        value = [
            Mapping(target = "id", ignore = true),
            Mapping(target = "modifyType", ignore = true),
            Mapping(target = "createTime", ignore = true),
            Mapping(target = "creator", ignore = true),
            Mapping(target = "updateTime", ignore = true),
            Mapping(target = "updater", ignore = true),
            Mapping(target = "deleted", ignore = true)
        ]
    )
    fun convert2Entity(request: RE): E
}