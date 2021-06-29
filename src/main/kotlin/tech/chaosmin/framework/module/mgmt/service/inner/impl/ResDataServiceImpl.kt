package tech.chaosmin.framework.module.mgmt.service.inner.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.domain.dao.ResDataDAO
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ResData
import tech.chaosmin.framework.module.mgmt.service.inner.ResDataService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ResDataServiceImpl : ServiceImpl<ResDataDAO, ResData>(), ResDataService