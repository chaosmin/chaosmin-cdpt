package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.ResDataDAO
import tech.chaosmin.framework.dao.dataobject.ResData
import tech.chaosmin.framework.service.ResDataService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ResDataServiceImpl : ServiceImpl<ResDataDAO, ResData>(), ResDataService