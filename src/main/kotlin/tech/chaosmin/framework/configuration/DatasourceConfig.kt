package tech.chaosmin.framework.configuration

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.apache.ibatis.reflection.MetaObject
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.chaosmin.framework.definition.SystemConst.INIT_SUCCESSFULLY
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*


@Configuration
open class DatasourceConfig {
    private val logger = LoggerFactory.getLogger(DatasourceConfig::class.java)

    /**
     * 数据库序列生成器
     */
    @Bean
    open fun oracleKeyGenerator(): OracleKeyGenerator {
        val oracleKeyGenerator = OracleKeyGenerator()
        logger.info(INIT_SUCCESSFULLY, oracleKeyGenerator.javaClass.name)
        return oracleKeyGenerator
    }

    @Bean
    open fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        interceptor.addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL))
        logger.info(INIT_SUCCESSFULLY, interceptor.javaClass.name)
        return interceptor
    }

    @Bean
    open fun metaObjectHandler() = object : MetaObjectHandler {
        override fun insertFill(metaObject: MetaObject) {
            val timestamp = Date()
            val username = SecurityUtil.getUsername()
            this.setFieldValByName("createTime", timestamp, metaObject)
            this.setFieldValByName("creator", username, metaObject)
            this.setFieldValByName("updateTime", timestamp, metaObject)
            this.setFieldValByName("updater", username, metaObject)
        }

        override fun updateFill(metaObject: MetaObject) {
            val timestamp = Date()
            val username = SecurityUtil.getUsername()
            this.setFieldValByName("updateTime", timestamp, metaObject)
            this.setFieldValByName("updater", username, metaObject)
        }
    }
}