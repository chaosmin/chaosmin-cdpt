package tech.chaosmin.framework.module.cdpt.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.entity.GoodsPlanEntity
import tech.chaosmin.framework.module.cdpt.entity.ProductEntity
import tech.chaosmin.framework.module.cdpt.entity.ProductPlanEntity
import tech.chaosmin.framework.module.mgmt.entity.UserEntity

/**
 * @author Romani min
 * @since 2020/12/23 16:13
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface GoodsPlanMapper {
    companion object {
        val INSTANCE: GoodsPlanMapper = Mappers.getMapper(GoodsPlanMapper::class.java)
    }

    fun convert2DO(source: GoodsPlanEntity?): GoodsPlan?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: GoodsPlan?): GoodsPlanEntity?

    fun convert2Entity(source: List<GoodsPlan>?): List<GoodsPlanEntity>?

    @Mappings(
        value = [
            Mapping(target = "userId", source = "arg1.id"),
            Mapping(target = "userName", source = "arg1.username"),
            Mapping(target = "partnerCode", source = "arg2.partnerCode"),
            Mapping(target = "partnerName", source = "arg2.partnerName"),
            Mapping(target = "productId", source = "arg2.id"),
            Mapping(target = "productCode", source = "arg2.productCode"),
            Mapping(target = "productName", source = "arg2.productName"),
            Mapping(target = "clauseUrl", source = "arg2.clauseUrl"),
            Mapping(target = "productPlanId", source = "arg3.id"),
            Mapping(target = "productPlanCode", source = "arg3.planCode"),
            Mapping(target = "productPlanName", source = "arg3.planName"),
            Mapping(target = "status", expression = "java(1)"),
            Mapping(target = "forSale", expression = "java(1)"),
            Mapping(target = "authorizeTime", expression = "java(new java.util.Date())"),
            Mapping(target = "saleStartTime", expression = "java(cn.hutool.core.date.DateUtil.beginOfDay(new java.util.Date()))"),
            Mapping(target = "saleEndTime", expression = "java(cn.hutool.core.date.DateUtil.offsetMonth(new java.util.Date(), 1200))"),
            Mapping(target = "deleted", expression = "java(0)"),
            Mapping(target = "id", ignore = true),
            Mapping(target = "createTime", ignore = true),
            Mapping(target = "creator", ignore = true),
            Mapping(target = "updateTime", ignore = true),
            Mapping(target = "updater", ignore = true),
            Mapping(target = "extraInfo", ignore = true)
        ]
    )
    fun buildDO(arg1: UserEntity, arg2: ProductEntity, arg3: ProductPlanEntity): GoodsPlan
}