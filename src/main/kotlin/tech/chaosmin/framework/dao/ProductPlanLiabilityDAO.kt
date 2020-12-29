package tech.chaosmin.framework.dao

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.dao.dataobject.ProductPlanLiability
import tech.chaosmin.framework.dao.dataobject.ext.ProductPlanLiabilityExt

/**
 * @author Romani min
 * @since 2020/12/9 13:49
 */
interface ProductPlanLiabilityDAO : BaseMapper<ProductPlanLiability> {
    fun pageExt(
        page: Page<ProductPlanLiabilityExt>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<ProductPlanLiabilityExt>
    ): IPage<ProductPlanLiabilityExt>
}