package tech.chaosmin.framework.module.mgmt.domain.dao

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.module.mgmt.domain.dataobject.User
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ext.UserExt

interface UserDAO : BaseMapper<User> {
    fun pageExt(
        page: Page<UserExt>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<UserExt>
    ): IPage<UserExt>
}