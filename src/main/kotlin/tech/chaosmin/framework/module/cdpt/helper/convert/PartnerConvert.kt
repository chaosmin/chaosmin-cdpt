package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.cdpt.entity.PartnerEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PartnerReq
import tech.chaosmin.framework.module.cdpt.entity.response.PartnerResp

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