package tech.chaosmin.framework.module.cdpt.service.inner.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.ReportSettlementComsDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ReportSettlementComs
import tech.chaosmin.framework.module.cdpt.service.inner.ReportSettlementComsService

/**
 * @author Romani min
 * @since 2021/6/29 15:48
 */
@Service
open class ReportSettlementComsServiceImpl : ServiceImpl<ReportSettlementComsDAO, ReportSettlementComs>(), ReportSettlementComsService