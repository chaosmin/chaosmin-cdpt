package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ProductAgreementDAO
import tech.chaosmin.framework.dao.dataobject.ProductAgreement
import tech.chaosmin.framework.service.ProductAgreementService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductAgreementServiceImpl : ServiceImpl<ProductAgreementDAO, ProductAgreement>(), ProductAgreementService