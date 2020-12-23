package tech.chaosmin.framework.handler.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.domain.entity.PartnerEntity
import tech.chaosmin.framework.domain.request.PartnerReq
import tech.chaosmin.framework.domain.response.PartnerResp
import tech.chaosmin.framework.handler.convert.base.BaseConvert

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface PartnerConvert : BaseConvert<PartnerEntity, PartnerReq, PartnerResp> {
    companion object {
        val INSTANCE: PartnerConvert = Mappers.getMapper(PartnerConvert::class.java)
    }
}