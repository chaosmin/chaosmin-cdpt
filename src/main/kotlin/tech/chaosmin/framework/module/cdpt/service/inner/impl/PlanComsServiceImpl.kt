package tech.chaosmin.framework.module.cdpt.service.inner.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PlanComsDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PlanComs
import tech.chaosmin.framework.module.cdpt.service.inner.PlanComsService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class PlanComsServiceImpl : ServiceImpl<PlanComsDAO, PlanComs>(), PlanComsService