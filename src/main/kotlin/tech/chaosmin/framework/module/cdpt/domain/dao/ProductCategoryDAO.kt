package tech.chaosmin.framework.module.cdpt.domain.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.module.cdpt.domain.dataobject.ProductCategory

/**
 * @author Romani min
 * @since 2020/12/9 13:49
 */
interface ProductCategoryDAO : BaseMapper<ProductCategory> {
    fun getByProductIds(@Param("productIds") productIds: List<Long>): List<ProductCategory>
}