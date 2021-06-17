package tech.chaosmin.framework.module.cdpt.service.inner.impl

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.PartnerDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Partner
import tech.chaosmin.framework.module.cdpt.service.inner.PartnerService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class PartnerServiceImpl : ServiceImpl<PartnerDAO, Partner>(), PartnerService {
    override fun listEqPartnerCode(code: String): List<Partner> {
        val wa = Wrappers.query<Partner>().eq("partner_code", code)
        return baseMapper.selectList(wa)
    }

    override fun listLikePartnerName(name: String): List<Partner> {
        val wa = Wrappers.query<Partner>().like("partner_name", name)
        return baseMapper.selectList(wa)
    }
}