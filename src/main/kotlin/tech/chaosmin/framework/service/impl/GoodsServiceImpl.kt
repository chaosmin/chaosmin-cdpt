package tech.chaosmin.framework.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.dao.GoodsDAO
import tech.chaosmin.framework.dao.dataobject.Goods
import tech.chaosmin.framework.service.GoodsService

@Service
class GoodsServiceImpl : ServiceImpl<GoodsDAO, Goods>(), GoodsService