package tech.chaosmin.framework.config

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.baomidou.mybatisplus.core.parser.ISqlParser
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import net.sf.jsqlparser.statement.delete.Delete
import org.apache.ibatis.reflection.MetaObject
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.chaosmin.framework.domain.const.SystemConst.INIT_SUCCESSFULLY
import tech.chaosmin.framework.utils.SecurityUtil
import java.util.*
import kotlin.collections.ArrayList

@Configuration
open class DatasourceConfig {
    private val logger = LoggerFactory.getLogger(DatasourceConfig::class.java)

    var systemTables: List<String> = mutableListOf()

    /**
     * 数据库序列生成器
     */
    @Bean
    open fun oracleKeyGenerator(): OracleKeyGenerator {
        val oracleKeyGenerator = OracleKeyGenerator()
        logger.info(INIT_SUCCESSFULLY, oracleKeyGenerator.javaClass.name)
        return oracleKeyGenerator
    }

    /**
     * mbp乐观锁组件
     */
    @Bean
    open fun optimisticLockerInterceptor(): OptimisticLockerInterceptor {
        val optimisticLockerInterceptor = OptimisticLockerInterceptor()
        logger.info(INIT_SUCCESSFULLY, optimisticLockerInterceptor.javaClass.name)
        return optimisticLockerInterceptor
    }

    /**
     * mbp分页拦截器
     */
    @Bean
    open fun paginationInterceptor(): PaginationInterceptor {
        val paginationInterceptor = PaginationInterceptor()
        if (!systemTables.isNullOrEmpty()) {
            val sqlParserList = ArrayList<ISqlParser>()
            // 攻击 SQL 阻断解析器、加入解析链
            sqlParserList.add(object : BlockAttackSqlParser() {
                override fun processDelete(delete: Delete) {
                    if (systemTables.contains(delete.table.name)) {
                        return
                    }
                    super.processDelete(delete)
                }
            })
            paginationInterceptor.apply { this.sqlParserList = sqlParserList }
        }
        logger.info(INIT_SUCCESSFULLY, paginationInterceptor.javaClass.name)
        return paginationInterceptor
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