package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.convert.base.KeyValueEnumMapper
import tech.chaosmin.framework.dao.dataobject.Product
import tech.chaosmin.framework.domain.entity.ProductEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface ProductMapper : BaseMapper<ProductEntity, Product> {
    companion object {
        val INSTANCE: ProductMapper = Mappers.getMapper(ProductMapper::class.java)
    }
}