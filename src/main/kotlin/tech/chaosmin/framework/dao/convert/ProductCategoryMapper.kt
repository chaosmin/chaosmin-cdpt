package tech.chaosmin.framework.dao.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.dao.convert.base.BaseMapper
import tech.chaosmin.framework.dao.dataobject.ProductCategory
import tech.chaosmin.framework.domain.entity.ProductCategoryEntity

/**
 * @author Romani min
 * @since 2020/12/23 21:37
 */
@Mapper
interface ProductCategoryMapper : BaseMapper<ProductCategoryEntity, ProductCategory> {
    companion object {
        val INSTANCE: ProductCategoryMapper = Mappers.getMapper(ProductCategoryMapper::class.java)
    }
}