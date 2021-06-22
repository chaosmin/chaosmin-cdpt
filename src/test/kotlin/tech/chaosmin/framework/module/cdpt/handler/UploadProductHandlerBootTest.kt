package tech.chaosmin.framework.module.cdpt.handler

import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import org.springframework.mock.web.MockMultipartFile
import tech.chaosmin.framework.BaseTestMain
import tech.chaosmin.framework.module.mgmt.entity.request.UploadFileReq
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/6/21 22:12
 */
internal class UploadProductHandlerBootTest : BaseTestMain() {
    @Resource
    lateinit var uploadProductHandler: UploadProductHandler

    @Test
    fun operate() {
        val fileForTest = "保险产品导入模板_1.xlsx"
        ClassPathResource(fileForTest).inputStream.use {
            val uploadFileReq = UploadFileReq()
            uploadFileReq.file = MockMultipartFile("file", fileForTest, null, it)
            uploadFileReq.fileName = uploadFileReq.file?.originalFilename
            uploadProductHandler.operate(uploadFileReq)
        }
    }
}