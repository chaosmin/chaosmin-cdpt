package tech.chaosmin.framework.module.cdpt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.cdpt.domain.dao.GoodsDAO
import tech.chaosmin.framework.module.cdpt.domain.dataobject.Goods
import tech.chaosmin.framework.module.cdpt.service.GoodsService

/**
 * @author Romani min
 * @since 2020/12/9 13:50
 */
@Service
open class GoodsServiceImpl : ServiceImpl<GoodsDAO, Goods>(), GoodsService