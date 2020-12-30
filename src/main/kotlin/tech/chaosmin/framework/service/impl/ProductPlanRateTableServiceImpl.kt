package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ProductPlanRateTableDAO
import tech.chaosmin.framework.dao.dataobject.ProductPlanRateTable
import tech.chaosmin.framework.service.ProductPlanRateTableService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ProductPlanRateTableServiceImpl : ServiceImpl<ProductPlanRateTableDAO, ProductPlanRateTable>(), ProductPlanRateTableService