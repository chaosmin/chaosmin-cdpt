package tech.chaosmin.framework.utils

import org.junit.jupiter.api.Test
import tech.chaosmin.framework.base.enums.BizNoTypeEnum

/**
 * @author Romani min
 * @since 2021/6/3 17:21
 */
internal class BizNoUtilTest {

    @Test
    fun nextBizNo() {
        println(BizNoUtil.nextBizNo(BizNoTypeEnum.DATETIME, 15))
        println(BizNoUtil.nextBizNo(BizNoTypeEnum.DATETIME, 15, "TST", "-"))
        println(BizNoUtil.nextBizNo(BizNoTypeEnum.TIMESTAMP, 15))
        println(BizNoUtil.nextBizNo(BizNoTypeEnum.TIMESTAMP, 15, "TST", "-"))
    }
}