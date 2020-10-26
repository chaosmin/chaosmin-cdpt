package tech.chaosmin.framework.domain.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.dataobject.Goods
import tech.chaosmin.framework.domain.request.share.GoodsShareRequestDTO
import tech.chaosmin.framework.domain.response.share.GoodsShareResponseDTO

@Mapper
interface GoodsConvert {
    companion object {
        val INSTANCE: GoodsConvert = Mappers.getMapper(GoodsConvert::class.java)
    }

    fun convertToShareResponse(goods: Goods): GoodsShareResponseDTO

    fun convertToShareResponse(authorities: List<Goods>): List<GoodsShareResponseDTO>

    fun convertToBaseBean(requestDTO: GoodsShareRequestDTO): Goods
}