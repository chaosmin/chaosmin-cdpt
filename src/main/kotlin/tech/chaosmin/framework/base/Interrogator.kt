/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * Interrogator.kt
 *
 * Basic interrogator class, basic functions required by abstract Interrogator.
 * The basic information needs to be converted into entity information when returning.
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.base

import com.baomidou.mybatisplus.core.metadata.IPage

/**
 * 基础数据查询逻辑 <p>
 * <p>
 * E: 中间态实体变量,需继承于[BaseEntity]
 * D: 底层实体变量,需继承与[BaseDO]
 * <p>
 * @author Romani min
 * @since 2020/12/23 17:05
 */
interface Interrogator<E : BaseEntity<E>, D : BaseDO> {
    /**
     * 根据ID查询
     * @param id 查询实体的主键ID
     * @return 实体中间态,未查询到时返回Null
     */
    fun getOne(id: Long): E?

    /**
     * 根据查询条件分页查询
     * @param cond 基于底层实体变量封装的查询条件[PageQuery]
     * @return 基于中间态返回的分页查询结果,未查询到时返回空页
     */
    fun page(cond: PageQuery<D>): IPage<E>
}