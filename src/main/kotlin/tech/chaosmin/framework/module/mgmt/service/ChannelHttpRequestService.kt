package tech.chaosmin.framework.module.mgmt.service

import com.baomidou.mybatisplus.extension.service.IService
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpRequestBase
import tech.chaosmin.framework.module.mgmt.domain.dataobject.ChannelHttpRequest

/**
 * @author Romani min
 * @since 2021/6/21 15:14
 */
interface ChannelHttpRequestService : IService<ChannelHttpRequest> {
    fun <T> saveRequest(request: HttpRequestBase, requestStr: String, entity: HttpEntity, func: (String) -> T): T
}