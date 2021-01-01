package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PlanLiabilityDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanLiability
import tech.chaosmin.framework.module.cdpt.service.PlanLiabilityService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class PlanLiabilityServiceImpl : ServiceImpl<PlanLiabilityDAO, PlanLiability>(), PlanLiabilityService