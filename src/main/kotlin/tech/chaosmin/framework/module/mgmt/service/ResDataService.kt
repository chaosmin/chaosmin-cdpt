package tech.chaosmin.framework.module.mgmt.service

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ResData

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface ResDataService : IService<ResData> {
    fun getValue(channel: String, itemKey: String, extend2: String): String?
}