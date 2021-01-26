package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PolicyInsurantDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.PolicyInsurant
import tech.chaosmin.framework.module.cdpt.service.PolicyInsurantService

/**
 * @author Romani min
 * @since 2021/1/26 15:31
 */
@Service
open class PolicyInsurantServiceImpl : ServiceImpl<PolicyInsurantDAO, PolicyInsurant>(), PolicyInsurantService {
}