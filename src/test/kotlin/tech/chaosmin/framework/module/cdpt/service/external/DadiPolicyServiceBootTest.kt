package tech.chaosmin.framework.module.cdpt.service.external

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.ChannelOpInfo
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.inner.PolicyDTO
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.DDRequestHead
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCPReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDCReq
import tech.chaosmin.framework.module.cdpt.entity.channel.dadi.request.obj.DDUReq
import tech.chaosmin.framework.utils.JsonUtil
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/6/17 21:44
 */
class DadiPolicyServiceBootTest : BaseTestMain() {
    @Resource
    lateinit var dadiPolicyService: DadiPolicyService

    @Test
    fun calculatePremium() {
        val request = DDReq<DDCReq>()
        request.requestHead = DDRequestHead("test-order-no-0100")
        request.requestBody = DDCReq().apply {
        }
        val result = dadiPolicyService.calculatePremium(request)
        println(JsonUtil.encode(result, true))
        Assertions.assertThat(result).isNotNull
    }

    @Test
    fun underwriting() {
        val request = DDReq<DDUReq>()
        request.requestHead = DDRequestHead("test-order-no-0101")
        request.requestBody = DDUReq().apply {
            this.policyDTO = PolicyDTO().apply {
                this.printNo = "printNo"
                this.businessNo = "businessNo"
            }
            this.channelOpInfoDTO = ChannelOpInfo()
        }
        val result = dadiPolicyService.underwriting(request)
        println(JsonUtil.encode(result, true))
        Assertions.assertThat(result).isNotNull
    }

    @Test
    fun cancelPolicy() {
        val request = DDReq<DDCPReq>()
        request.requestHead = DDRequestHead("test-order-no-0102")
        request.requestBody = DDCPReq().apply {
        }
        val result = dadiPolicyService.cancelPolicy(request)
        println(JsonUtil.encode(result, true))
        Assertions.assertThat(result).isNotNull
    }
}