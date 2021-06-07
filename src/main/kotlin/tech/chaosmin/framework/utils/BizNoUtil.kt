package tech.chaosmin.framework.utils

import cn.hutool.core.date.DateUtil
import cn.hutool.extra.spring.SpringUtil
import org.springframework.data.redis.core.StringRedisTemplate
import tech.chaosmin.framework.base.enums.BizNoTypeEnum
import java.util.*

/**
 * @author Romani min
 * @since 2021/1/27 10:27
 */
object BizNoUtil {
    private const val BIZ_NO_NAMESPACE = "SYS:BIZ-NO"
    private var redisHelper: StringRedisTemplate? = null
    private var seq = 0L

    init {
        if (SpringUtil.getApplicationContext() != null) {
            // 如果初始化了Redis组件，则从redis缓存中获取相关
            redisHelper = SpringUtil.getBean(StringRedisTemplate::class.java)
        }
    }

    fun nextBizNo(type: BizNoTypeEnum, sequenceLength: Int, prefix: String = "", spliterator: String = ""): String {
        val stringJoiner = StringJoiner(spliterator)
        stringJoiner.add(prefix)
        var length = sequenceLength
        when (type) {
            BizNoTypeEnum.DATE -> {
                stringJoiner.add(DateUtil.format(Date(), "yyyyMMdd"))
                length -= 8
            }
            BizNoTypeEnum.TIME -> {
                stringJoiner.add(DateUtil.format(Date(), "HHmmss"))
                length -= 6
            }
            BizNoTypeEnum.DATETIME -> {
                stringJoiner.add(DateUtil.format(Date(), "yyyyMMddHHmmss"))
                length -= 14
            }
            BizNoTypeEnum.TIMESTAMP -> {
                stringJoiner.add(Date().time.toString())
                length -= 13
            }
            else -> stringJoiner.add("")
        }
        stringJoiner.add(nextSequence())
        return stringJoiner.toString()
    }

    private fun nextSequence(length: Int = 4): String {
        val sequence = if (redisHelper != null) {
            redisHelper!!.opsForValue().increment(BIZ_NO_NAMESPACE) ?: ++seq
        } else ++seq
        return "%0${length}d".format(sequence)
    }
}