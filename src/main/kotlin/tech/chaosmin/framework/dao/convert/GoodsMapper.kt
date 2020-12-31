package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.convert.base.KeyValueEnumMapper
import tech.chaosmin.framework.dao.dataobject.Goods
import tech.chaosmin.framework.domain.entity.GoodsEntity

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