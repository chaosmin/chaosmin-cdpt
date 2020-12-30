package tech.chaosmin.framework.handler

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.nhaarman.mockitokotlin2.anyOrNull
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.core.io.ClassPathResource
import org.springframework.mock.web.MockMultipartFile
import tech.chaosmin.framework.dao.dataobject.Partner
import tech.chaosmin.framework.dao.dataobject.ProductCategory
import tech.chaosmin.framework.domain.RestResultExt
import tech.chaosmin.framework.domain.entity.ProductEntity
import tech.chaosmin.framework.domain.entity.ProductPlanEntity
import tech.chaosmin.framework.domain.request.UploadFileReq
import tech.chaosmin.framework.service.PartnerService
import tech.chaosmin.framework.service.ProductCategoryService
import tech.chaosmin.framework.utils.JsonUtil

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

    @Mock
    lateinit var modifyProductHandler: ModifyProductHandler

    @Mock
    lateinit var modifyProductPlanHandler: ModifyProductPlanHandler

    private val partner = Partner()
    private val productCategory = ProductCategory()
    private val productEntity = ProductEntity()
    private val fileForTest = "保险产品导入模板.xlsx"

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        // mock partner data
        partner.id = 1
        partner.partnerName = "MockPartner"
        // mock product category data
        productCategory.id = 1
        // set mock function return
        `when`(partnerService.listEqPartnerCode(anyString())).thenReturn(listOf(partner))
        `when`(categoryService.list(any<Wrapper<ProductCategory>>())).thenReturn(listOf(productCategory))
        `when`(modifyProductHandler.operate(anyOrNull())).thenReturn(RestResultExt.successRestResult(productEntity))
        `when`(modifyProductPlanHandler.operate(anyOrNull())).thenReturn(RestResultExt.successRestResult())
    }

    @Test
    fun operate() {
        val resource = ClassPathResource(fileForTest)
        resource.inputStream.use {
            val arg = UploadFileReq()
            arg.file = MockMultipartFile("file", fileForTest, null, it)
            arg.fileName = arg.file?.originalFilename
            val result = uploadProductHandler.operate(arg)
            assertThat(result).isNotNull
            assertThat(result.success).isNotNull().isTrue()
            assertThat(result.data).isNotNull
            println(JsonUtil.encode(result.data, true))
        }
    }
}