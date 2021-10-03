package tech.chaosmin.framework.module.cdpt.domain.dao

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyEx

/**
 * @author Romani min
 * @since 2021/1/26 15:29
 */
interface PolicyDAO : BaseMapper<Policy> {
    fun listExt(@Param(Constants.WRAPPER) queryWrapper: Wrapper<PolicyEx>): List<PolicyEx>

    fun pageExt(page: Page<PolicyEx>, @Param(Constants.WRAPPER) queryWrapper: Wrapper<PolicyEx>): IPage<PolicyEx>
}