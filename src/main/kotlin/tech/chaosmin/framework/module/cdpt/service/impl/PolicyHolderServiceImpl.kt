package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PolicyHolderDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyHolder
import tech.chaosmin.framework.module.cdpt.service.PolicyHolderService

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
@Service
open class PolicyHolderServiceImpl : ServiceImpl<PolicyHolderDAO, PolicyHolder>(), PolicyHolderService {
}