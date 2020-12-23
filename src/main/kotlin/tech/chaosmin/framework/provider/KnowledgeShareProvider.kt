package tech.chaosmin.framework.provider

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.dao.dataobject.Knowledge
import tech.chaosmin.framework.domain.RestResult
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.KnowledgeEntity
import tech.chaosmin.framework.domain.request.KnowledgeReq
import tech.chaosmin.framework.domain.response.KnowledgeResp
import tech.chaosmin.framework.handler.ModifyKnowledgeHandler
import tech.chaosmin.framework.handler.convert.KnowledgeConvert
import tech.chaosmin.framework.handler.logic.KnowledgeQueryLogic
import tech.chaosmin.framework.utils.RequestUtil
import tech.chaosmin.framework.web.service.KnowledgeShareService
import javax.servlet.http.HttpServletRequest

/**
 * @author Romani min
 * @since 2020/12/10 13:48
 */
@RestController
open class KnowledgeShareProvider(
    private val knowledgeQueryLogic: KnowledgeQueryLogic,
    private val modifyKnowledgeHandler: ModifyKnowledgeHandler
) : KnowledgeShareService {
    override fun selectById(id: Long): RestResult<KnowledgeResp?> {
        val knowledge = knowledgeQueryLogic.get(id)
        return if (knowledge == null) RestResultExt.successRestResult()
        else RestResultExt.successRestResult(KnowledgeConvert.INSTANCE.convert2Resp(knowledge))
    }

    override fun page(request: HttpServletRequest): RestResult<IPage<KnowledgeResp>> {
        val queryCondition = RequestUtil.getQueryCondition<Knowledge>(request)
        val page = knowledgeQueryLogic.page(queryCondition)
        return RestResultExt.successRestResult(page.convert(KnowledgeConvert.INSTANCE::convert2Resp))
    }

    override fun save(req: KnowledgeReq): RestResult<KnowledgeResp> {
        val knowledge = KnowledgeConvert.INSTANCE.convert2Entity(req)
        knowledge.save()
        return RestResultExt.execute(modifyKnowledgeHandler, knowledge, KnowledgeConvert::class.java)
    }

    override fun update(id: Long, req: KnowledgeReq): RestResult<KnowledgeResp> {
        val knowledge = KnowledgeConvert.INSTANCE.convert2Entity(req)
        knowledge.update(id)
        return RestResultExt.execute(modifyKnowledgeHandler, knowledge, KnowledgeConvert::class.java)
    }

    override fun delete(id: Long): RestResult<KnowledgeResp> {
        val knowledge = KnowledgeEntity(id)
        knowledge.remove()
        return RestResultExt.execute(modifyKnowledgeHandler, knowledge, KnowledgeConvert::class.java)
    }
}