package tech.chaosmin.framework.module.cdpt.domain.dao

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Policy
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ext.PolicyExt

/**
 * @author Romani min
 * @since 2021/1/26 15:29
 */
interface PolicyDAO : BaseMapper<Policy> {
    fun pageExt(
        page: Page<PolicyExt>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<PolicyExt>
    ): IPage<PolicyExt>
}