package tech.chaosmin.framework.dao

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import tech.chaosmin.framework.dao.dataobject.Product
import tech.chaosmin.framework.dao.dataobject.ext.ProductExt

/**
 * @author Romani min
 * @since 2020/12/9 13:49
 */
interface ProductDAO : BaseMapper<Product> {
    fun pageExt(
        page: Page<ProductExt>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<ProductExt>
    ): IPage<ProductExt>

    @Insert(
        value = ["<script> insert into product_category_relation(product_id,product_category_id) VALUES "
                + "<foreach collection='categoryIds' item='id' separator=','>"
                + "(#{productId},#{id}) </foreach></script>"]
    )
    fun addRelations(@Param("productId") productId: Long, @Param("categoryIds") categoryIds: List<Long>)
}