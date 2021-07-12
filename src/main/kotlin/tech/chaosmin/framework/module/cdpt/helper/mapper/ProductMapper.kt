package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Product
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductExt
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface ProductMapper {
    companion object {
        val INSTANCE: ProductMapper = Mappers.getMapper(ProductMapper::class.java)
    }

    fun convert2DO(source: ProductEntity?): Product?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: Product?): ProductEntity?

    fun convert2Entity(source: ProductExt?): ProductEntity?

    fun convert2Entity(source: List<Product>?): List<ProductEntity>?
}