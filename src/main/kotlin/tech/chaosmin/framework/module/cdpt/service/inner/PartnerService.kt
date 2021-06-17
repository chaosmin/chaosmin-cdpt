package tech.chaosmin.framework.module.cdpt.service.inner

import com.baomidou.mybatisplus.extension.service.IService
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Partner

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
interface PartnerService : IService<Partner> {
    fun listEqPartnerCode(code: String): List<Partner>
    fun listLikePartnerName(name: String): List<Partner>
}