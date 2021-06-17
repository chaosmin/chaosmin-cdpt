package tech.chaosmin.framework.module.cdpt.service.inner

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.chaosmin.framework.BaseTestMain
import javax.annotation.Resource

/**
 * @author Romani min
 * @since 2021/1/18 15:56
 */
class ProductCategoryServiceBootTest : BaseTestMain() {
    @Resource
    lateinit var productCategoryService: ProductCategoryService

    @Test
    fun getByProductIds() {
        val productCategories = productCategoryService.getByProductIds(listOf(1, 2))
        assertThat(productCategories).isNotNull.isNotEmpty
    }
}