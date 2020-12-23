package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.PartnerDAO
import tech.chaosmin.framework.dao.dataobject.Partner
import tech.chaosmin.framework.service.PartnerService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class PartnerServiceImpl : ServiceImpl<PartnerDAO, Partner>(), PartnerService