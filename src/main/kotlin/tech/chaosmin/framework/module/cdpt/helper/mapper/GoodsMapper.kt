package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.BaseMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Goods
import tech.chaosmin.framework.module.cdpt.entity.GoodsEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface GoodsMapper : BaseMapper<GoodsEntity, Goods> {
    companion object {
        val INSTANCE: GoodsMapper = Mappers.getMapper(GoodsMapper::class.java)
    }
}