package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.PolicyDAO
import tech.chaosmin.framework.dao.dataobject.Policy
import tech.chaosmin.framework.service.PolicyService

@Service
open class PolicyServiceImpl : ServiceImpl<PolicyDAO, Policy>(), PolicyService