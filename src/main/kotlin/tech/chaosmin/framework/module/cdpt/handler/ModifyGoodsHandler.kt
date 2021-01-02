package tech.chaosmin.framework.module.cdpt.handler

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.chaosmin.framework.base.AbstractTemplateOperate
import tech.chaosmin.framework.base.RestResult
import tech.chaosmin.framework.base.enums.ErrorCodeEnum
import tech.chaosmin.framework.base.enums.ModifyTypeEnum
import tech.chaosmin.framework.exception.FrameworkException
import tech.chaosmin.framework.module.cdpt.entity.GoodsEntity
import tech.chaosmin.framework.module.cdpt.helper.mapper.GoodsMapper
import tech.chaosmin.framework.module.cdpt.service.GoodsService

/**
 * @author Romani min
 * @since 2020/12/23 17:12
 */
@Component
open class ModifyGoodsHandler(private val goodsService: GoodsService) :
    AbstractTemplateOperate<GoodsEntity, GoodsEntity>() {
    override fun validation(arg: GoodsEntity, result: RestResult<GoodsEntity>) {
        if (arg.modifyType == null) {
            throw FrameworkException(ErrorCodeEnum.PARAM_IS_NULL.code, "modifyType");
        }
    }

    @Transactional
    override fun processor(arg: GoodsEntity, result: RestResult<GoodsEntity>): RestResult<GoodsEntity> {
        val goods = GoodsMapper.INSTANCE.convert2DO(arg)
        when (arg.modifyType) {
            ModifyTypeEnum.SAVE -> goodsService.save(goods)
            ModifyTypeEnum.UPDATE -> goodsService.updateById(goods)
            ModifyTypeEnum.REMOVE -> goodsService.remove(Wrappers.query(goods))
        }
        return result.success(arg)
    }
}