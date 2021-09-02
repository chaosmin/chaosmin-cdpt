package tech.chaosmin.framework.module.payment.helper.convert

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.payment.entity.PaymentTransactionEntity
import tech.chaosmin.framework.module.payment.entity.wechat.request.NativePayReq

/**
 * @author Romani min
 * @since 2021/8/25 13:29
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PaymentTransactionConvert {
    companion object {
        val INSTANCE: PaymentTransactionConvert = Mappers.getMapper(PaymentTransactionConvert::class.java)
    }

    // fun convert2Resp(source: PaymentTransactionEntity): PolicyKhsResp

    // fun convert2Resp(source: List<PolicyKhsEntity>): List<PolicyKhsResp>

    @Mappings(
        Mapping(target = "amount", expression = "java(request.getAmount().getTotal().longValue())"),
        Mapping(target = "outTradeNo", expression = "java(request.getOut_trade_no())")
    )
    fun convert2Entity(request: NativePayReq): PaymentTransactionEntity
}