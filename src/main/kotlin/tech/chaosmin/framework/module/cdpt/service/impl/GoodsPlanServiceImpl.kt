package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.GoodsPlanDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.service.GoodsPlanService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class GoodsPlanServiceImpl : ServiceImpl<GoodsPlanDAO, GoodsPlan>(), GoodsPlanService