package tech.chaosmin.framework.module.mgmt.service.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.domain.dao.ResDataDAO
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ResData
import tech.chaosmin.framework.module.mgmt.service.ResDataService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class ResDataServiceImpl : ServiceImpl<ResDataDAO, ResData>(), ResDataService {
    // 增加缓存处理
    @Cacheable(value = ["res-data"], key = """#channel-#itemKey-#extend2""", unless = "#result == null")
    override fun getValue(channel: String, itemKey: String, extend2: String): String? {
        val ew = Wrappers.query<ResData>().eq("extend1", channel)
            .eq("item_key", itemKey).eq("extend2", extend2)
        return baseMapper.selectList(ew).firstOrNull()?.itemCode
    }
}