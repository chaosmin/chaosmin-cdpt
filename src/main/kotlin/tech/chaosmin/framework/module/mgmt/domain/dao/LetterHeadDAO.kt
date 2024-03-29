package tech.chaosmin.framework.module.mgmt.domain.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.module.mgmt.domain.dataobject.LetterHead

/**
 * @author Romani min
 * @since 2020/12/9 13:49
 */
interface LetterHeadDAO : BaseMapper<LetterHead> {
    fun fetchHeadByDepartmentId(@Param("id") departmentId: Long): Set<LetterHead>

    fun realDeleteByIds(@Param("ids") ids: List<Long>)

    fun realDeleteByDepartmentId(@Param("id") departmentId: Long)
}