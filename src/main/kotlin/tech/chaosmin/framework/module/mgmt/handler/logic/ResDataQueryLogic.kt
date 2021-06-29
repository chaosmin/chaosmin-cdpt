package tech.chaosmin.framework.module.mgmt.handler.logic

import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.stereotype.Component
import tech.chaosmin.framework.base.BaseQueryLogic
import tech.chaosmin.framework.base.PageQuery
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ResData
import tech.chaosmin.framework.module.mgmt.entity.ResDataEntity
import tech.chaosmin.framework.module.mgmt.helper.mapper.ResDataMapper
import tech.chaosmin.framework.module.mgmt.service.inner.ResDataService

/**
 * @author Romani min
 * @since 2021/6/29 11:10
 */
@Component
class ResDataQueryLogic(private val resDataService: ResDataService) : BaseQueryLogic<ResDataEntity, ResData> {
    override fun get(id: Long): ResDataEntity? {
        val resData = resDataService.getById(id)
        return ResDataMapper.INSTANCE.convert2Entity(resData)
    }

    override fun page(cond: PageQuery<ResData>): IPage<ResDataEntity?> {
        val page = resDataService.page(cond.page, cond.wrapper)
        return page.convert(ResDataMapper.INSTANCE::convert2Entity)
    }
}