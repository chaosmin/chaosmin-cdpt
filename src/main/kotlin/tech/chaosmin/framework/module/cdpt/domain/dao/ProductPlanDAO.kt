package tech.chaosmin.framework.module.cdpt.domain.dao

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.ProductPlanEx

/**
 * @author Romani min
 * @since 2020/12/9 13:49
 */
interface ProductPlanDAO : BaseMapper<ProductPlan> {
    fun pageExt(page: Page<ProductPlanEx>, @Param(Constants.WRAPPER) queryWrapper: Wrapper<ProductPlanEx>): IPage<ProductPlanEx>
}