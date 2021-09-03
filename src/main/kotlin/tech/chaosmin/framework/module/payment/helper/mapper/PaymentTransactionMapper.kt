package tech.chaosmin.framework.module.payment.helper.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.payment.domain.dataobject.PaymentTransaction
import tech.chaosmin.framework.module.payment.entity.PaymentTransactionEntity

/**
 * @author Romani min
 * @since 2021/8/25 13:30
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PaymentTransactionMapper {
    companion object {
        val INSTANCE: PaymentTransactionMapper = Mappers.getMapper(PaymentTransactionMapper::class.java)
    }

    fun convert2DO(source: PaymentTransactionEntity?): PaymentTransaction?

    @Mapping(target = "modifyType", ignore = true)
    fun convert2Entity(source: PaymentTransaction?): PaymentTransactionEntity?

    fun convert2Entity(source: List<PaymentTransaction>?): List<PaymentTransactionEntity>?
}