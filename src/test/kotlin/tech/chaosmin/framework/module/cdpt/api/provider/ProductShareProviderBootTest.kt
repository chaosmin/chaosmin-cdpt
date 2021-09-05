package tech.chaosmin.framework.module.cdpt.api.provider

import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.module.cdpt.api.ProductAPI
import tech.chaosmin.framework.module.mgmt.entity.request.UploadFileReq
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/6/21 22:09
 */
internal class ProductShareProviderBootTest : BaseTestMain() {
    @Resource
    lateinit var productShareService: ProductAPI

    @Test
    fun upload() {
        val uploadFileReq = UploadFileReq()
        productShareService.upload(uploadFileReq)
    }
}