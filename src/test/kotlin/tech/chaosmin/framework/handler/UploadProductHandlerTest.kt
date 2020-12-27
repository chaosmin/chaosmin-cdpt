package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.conditions.Wrapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.MockitoAnnotations
import org.springframework.mock.web.MockMultipartFile
import tech.chaosmin.framework.dao.dataobject.Partner
import tech.chaosmin.framework.dao.dataobject.ProductCategory
import tech.chaosmin.framework.domain.request.UploadFileReq
import tech.chaosmin.framework.service.PartnerService
import tech.chaosmin.framework.service.ProductCategoryService
import tech.chaosmin.framework.utils.JsonUtil
import java.io.File

/**
 * @author Romani min
 * @since 2020/12/26 10:28
 */
internal class UploadProductHandlerTest {
    @InjectMocks
    lateinit var uploadProductHandler: UploadProductHandler

    @Mock
    lateinit var partnerService: PartnerService

    @Mock
    lateinit var categoryService: ProductCategoryService

    var partner = Partner()
    var productCategory = ProductCategory()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        partner.id = 1
        partner.partnerName = "MockPartner"

        productCategory.id = 1

        `when`(partnerService.list(any<Wrapper<Partner>>())).thenReturn(listOf(partner))
        `when`(categoryService.list(any<Wrapper<ProductCategory>>())).thenReturn(listOf(productCategory))
    }

    @Test
    fun operate() {
        File("/Users/romani/Desktop/永诚保险_快乐出境险.xlsx").inputStream().use {
            val arg = UploadFileReq()
            arg.file = MockMultipartFile("file", "永诚保险_快乐出境险.xlsx", null, it)
            arg.fileName = arg.file?.originalFilename
            val result = uploadProductHandler.operate(arg)
            assertThat(result).isNotNull
            assertThat(result.success).isNotNull().isTrue()
            assertThat(result.data).isNotNull
            println(JsonUtil.encode(result.data, true))
        }
    }
}