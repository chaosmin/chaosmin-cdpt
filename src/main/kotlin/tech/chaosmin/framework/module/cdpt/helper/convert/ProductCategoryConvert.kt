package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.module.cdpt.entity.ProductCategoryEntity
import tech.chaosmin.framework.module.cdpt.entity.request.ProductCategoryReq
import tech.chaosmin.framework.module.cdpt.entity.response.ProductCategoryResp

/**
 * @author Romani min
 * @since 2020/12/23 21:39
 */
@Mapper
interface ProductCategoryConvert {
    companion object {
        val INSTANCE: ProductCategoryConvert = Mappers.getMapper(ProductCategoryConvert::class.java)
    }

    fun convert2Resp(source: ProductCategoryEntity): ProductCategoryResp

    fun convert2Resp(source: List<ProductCategoryEntity>): List<ProductCategoryResp>

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
    fun convert2Entity(request: ProductCategoryReq): ProductCategoryEntity

}