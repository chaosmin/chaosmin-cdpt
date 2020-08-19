package tech.chaosmin.framework.domain

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page

class PageQueryDTO<T, E>(var page: Page<E>, var wrapper: QueryWrapper<T>)