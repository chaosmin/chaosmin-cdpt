package tech.chaosmin.framework.module.payment.logic.convert

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import tech.chaosmin.framework.base.IMapper
import tech.chaosmin.framework.base.KeyValueEnumMapper
import tech.chaosmin.framework.module.payment.domain.dataobject.PaymentTransaction
import tech.chaosmin.framework.module.payment.entity.PaymentTransEntity

/**
 * @author Romani min
 * @since 2021/8/25 13:30
 */
@Mapper(uses = [KeyValueEnumMapper::class])
interface PaymentTransMapper : IMapper<PaymentTransEntity, PaymentTransaction> {
    companion object {
        val INSTANCE: PaymentTransMapper = Mappers.getMapper(PaymentTransMapper::class.java)
    }
}