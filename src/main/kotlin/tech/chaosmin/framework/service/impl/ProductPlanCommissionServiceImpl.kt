package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ProductPlanCommissionDAO
import tech.chaosmin.framework.dao.dataobject.ProductPlanCommission
import tech.chaosmin.framework.service.ProductPlanCommissionService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductPlanCommissionServiceImpl : ServiceImpl<ProductPlanCommissionDAO, ProductPlanCommission>(), ProductPlanCommissionService