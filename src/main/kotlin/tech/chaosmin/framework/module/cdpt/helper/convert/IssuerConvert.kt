package tech.chaosmin.framework.module.cdpt.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.cdpt.entity.OrderEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyHolderEntity
import tech.chaosmin.framework.module.cdpt.entity.PolicyInsurantEntity
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyInsuredReq
import tech.chaosmin.framework.module.cdpt.entity.request.PolicyIssueReq

/**
 * @author Romani min
 * @since 2021/6/8 18:22
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface IssuerConvert {
    companion object {
        val INSTANCE: IssuerConvert = Mappers.getMapper(IssuerConvert::class.java)
    }

    @Mapping(target = "id", source = "orderId")
    fun convert2OrderEntity(arg: PolicyIssueReq): OrderEntity

    @Mappings(
        value = [
            Mapping(target = "holder", expression = "java(convert2PolicyHolderEntity(arg))"),
            Mapping(target = "effectiveTime", source = "startTime"),
            Mapping(target = "expiryTime", source = "endTime"),
            Mapping(target = "travelDestination", source = "address"),
            Mapping(target = "insuredSize", expression = "java(arg.getInsuredList().size())")
        ]
    )
    fun convert2PolicyEntity(arg: PolicyIssueReq): PolicyEntity

    @Mappings(
        value = [
            Mapping(target = "name", source = "policyHolderName"),
            Mapping(target = "certiNo", source = "policyHolderCerti"),
            Mapping(target = "partyType", expression = "java(tech.chaosmin.framework.base.enums.CustomerTypeEnum.COMPANY)"),
            Mapping(target = "mainInsuredRelation", expression = "java(1)")
        ]
    )
    fun convert2PolicyHolderEntity(arg: PolicyIssueReq): PolicyHolderEntity

    @Mappings(
        value = [
            Mapping(target = "birthday", source = "dateOfBirth"),
            Mapping(target = "phoneNo", source = "mobile"),
            Mapping(target = "partyType", expression = "java(tech.chaosmin.framework.base.enums.CustomerTypeEnum.PERSON)")
        ]
    )
    fun convert2PolicyInsurantEntity(arg: PolicyInsuredReq): PolicyInsurantEntity
}