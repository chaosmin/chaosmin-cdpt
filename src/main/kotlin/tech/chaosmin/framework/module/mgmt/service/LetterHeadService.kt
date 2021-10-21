package tech.chaosmin.framework.module.mgmt.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.LetterHead

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface LetterHeadService : IService<LetterHead> {
    fun fetchByUserId(userId: Long): Set<LetterHead>
    fun realDeleteByIds(ids: List<Long>)
    fun realDeleteByUserId(userId: Long)
}