package tech.chaosmin.framework.module.cdpt.domain.dao

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.module.cdpt.domain.dataobject.GoodsPlan
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.GoodsPlanExt

/**
 * @author Romani min
 * @since 2020/12/9 13:49
 */
interface GoodsPlanDAO : BaseMapper<GoodsPlan> {
    fun getByIdExt(@Param("id") id: Long): GoodsPlanExt?

    fun pageExt(
        page: Page<GoodsPlanExt>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<GoodsPlanExt>
    ): IPage<GoodsPlanExt>
}