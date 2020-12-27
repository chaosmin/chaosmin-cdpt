package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ProductAgreementDAO
import tech.chaosmin.framework.dao.dataobject.ProductAgreement
import tech.chaosmin.framework.service.ProductAgreementService
import java.util.*

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductAgreementServiceImpl : ServiceImpl<ProductAgreementDAO, ProductAgreement>(), ProductAgreementService {
    override fun getNoticeText(productId: Long): String {
        val agreement = baseMapper.selectOne(QueryWrapper<ProductAgreement>().eq("product_id", productId))
        val stringJoiner = StringJoiner("<br>")
        agreement.specialAgreement?.run {
            stringJoiner.add("特别约定").add(this)
        }
        agreement.notice?.run {
            stringJoiner.add("投保须知").add(this)
        }
        return stringJoiner.toString()
    }
}