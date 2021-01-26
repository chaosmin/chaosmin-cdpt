package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PolicyDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.service.PolicyService

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
@Service
open class PolicyServiceImpl : ServiceImpl<PolicyDAO, Policy>(), PolicyService {
}