package tech.chaosmin.framework.module.mgmt.api.provider

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.web.bind.annotation.RestController
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.RestResultExt
import tech.chaosmin.framework.module.mgmt.api.ResDataShareService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ResData
import tech.chaosmin.framework.module.mgmt.entity.response.ResDataResp
import tech.chaosmin.framework.module.mgmt.helper.convert.ResDataConvert
import tech.chaosmin.framework.module.mgmt.logic.interrogator.ResDataInterrogator

/**
 * @author Romani min
 * @since 2021/6/29 11:09
 */
@RestController
open class ResDataShareProvider(private val resDataInterrogator: ResDataInterrogator) : ResDataShareService {
    override fun list(channel: String, type: List<String>): RestResult<List<ResDataResp>> {
        val pq = PageQuery<ResData>(
            Page(0, 500),
            Wrappers.query<ResData>().eq("extend1", channel).`in`("item_key", type)
        )
        val page = resDataInterrogator.page(pq)
        val list = page.convert(ResDataConvert.INSTANCE::convert2Resp).records.filterNotNull()
        return RestResultExt.successRestResult(list)
    }
}