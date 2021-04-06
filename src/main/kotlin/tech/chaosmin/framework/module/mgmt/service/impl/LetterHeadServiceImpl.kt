package tech.chaosmin.framework.module.mgmt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.domain.dao.LetterHeadDAO
import tech.chaosmin.framework.module.mgmt.domain.dataobject.LetterHead
import tech.chaosmin.framework.module.mgmt.service.LetterHeadService

/**
 * @author Romani min
 * @since 2020/12/9 13:51
 */
@Service
open class LetterHeadServiceImpl : ServiceImpl<LetterHeadDAO, LetterHead>(), LetterHeadService