package tech.chaosmin.framework.module.mgmt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseConvert
import tech.chaosmin.framework.module.mgmt.entity.ResDataEntity
import tech.chaosmin.framework.module.mgmt.entity.request.ResDataReq
import tech.chaosmin.framework.module.mgmt.entity.response.ResDataResp

@Mapper
interface ResDataConvert : BaseConvert<ResDataEntity, ResDataReq, ResDataResp> {
    companion object {
        val INSTANCE: ResDataConvert = Mappers.getMapper(ResDataConvert::class.java)
    }
}