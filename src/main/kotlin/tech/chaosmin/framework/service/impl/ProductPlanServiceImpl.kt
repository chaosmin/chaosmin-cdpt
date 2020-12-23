package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ProductPlanDAO
import tech.chaosmin.framework.dao.dataobject.ProductPlan
import tech.chaosmin.framework.service.ProductPlanService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductPlanServiceImpl : ServiceImpl<ProductPlanDAO, ProductPlan>(), ProductPlanService