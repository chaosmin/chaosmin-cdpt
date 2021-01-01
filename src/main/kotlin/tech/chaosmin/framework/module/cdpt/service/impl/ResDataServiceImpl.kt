package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.ResDataDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ResData
import tech.chaosmin.framework.module.cdpt.service.ResDataService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ResDataServiceImpl : ServiceImpl<ResDataDAO, ResData>(), ResDataService