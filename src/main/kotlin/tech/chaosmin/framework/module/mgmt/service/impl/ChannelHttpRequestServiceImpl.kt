package tech.chaosmin.framework.module.mgmt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import tech.chaosmin.framework.module.mgmt.domain.dao.ChannelHttpRequestDAO
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ChannelHttpRequest
import tech.chaosmin.framework.module.mgmt.service.ChannelHttpRequestService

/**
 * @author Romani min
 * @since 2021/6/21 15:15
 */
@Service
class ChannelHttpRequestServiceImpl : ServiceImpl<ChannelHttpRequestDAO, ChannelHttpRequest>(), ChannelHttpRequestService