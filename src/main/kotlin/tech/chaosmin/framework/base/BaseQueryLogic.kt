package tech.chaosmin.framework.base

import com.baomidou.mybatisplus.core.metadata.IPage

/**
 * 基础数据查询逻辑 <br/>
 * <br/>
 * E: 中间态实体变量,需继承于[BaseEntity]
 * D: 底层实体变量,需继承与[BaseDO]
 * <br/>
 * @author Romani min
 * @since 2020/12/23 17:05
 */
interface BaseQueryLogic<E : BaseEntity, D : BaseDO> {
    /**
     * 根据ID查询
     * @param id 查询实体的主键ID
     * @return 实体中间态,未查询到时返回Null
     */
    fun get(id: Long): E?

    /**
     * 根据查询条件分页查询
     * @param cond 基于底层实体变量封装的查询条件[PageQuery]
     * @return 基于中间态返回的分页查询结果,未查询到时返回空页
     */
    fun page(cond: PageQuery<D>): IPage<E?>
}