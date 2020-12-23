package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.KnowledgeDAO
import tech.chaosmin.framework.dao.dataobject.Knowledge
import tech.chaosmin.framework.service.KnowledgeService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class KnowledgeServiceImpl : ServiceImpl<KnowledgeDAO, Knowledge>(), KnowledgeService