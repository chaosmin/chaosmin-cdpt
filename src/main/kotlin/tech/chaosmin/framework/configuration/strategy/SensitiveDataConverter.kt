/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * SensitiveDataConverter.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.configuration.strategy

import ch.qos.logback.classic.pattern.MessageConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import tech.chaosmin.framework.base.SensitiveDataRule
import tech.chaosmin.framework.utils.JsonUtil
import tech.chaosmin.framework.utils.SensitiveProcessUtils

/**
 * @author Romani min
 * @since 2021/9/8 17:23
 */
class SensitiveDataConverter : MessageConverter() {
    private val sensitiveDataRules = mutableSetOf<SensitiveDataRule>()

    override fun convert(event: ILoggingEvent): String {
        val allowRun = context.getProperty("SensitiveDataAllowRun")
        if (allowRun == "true") {
            val context = context
            val keys = arrayOf("SensitiveDataKeys", "SensitiveDataKeys1", "SensitiveDataKeys2", "SensitiveDataKeys3")
            for (key in keys) {
                val dataKeys = context.getProperty(key)
                val list: List<SensitiveDataRule> = JsonUtil.convert2List(dataKeys, SensitiveDataRule::class.java)
                if (list.isNotEmpty()) sensitiveDataRules.addAll(list)
            }
        }

        // 获取原始日志
        val originMessage = event.formattedMessage
        return if ("true" != allowRun || sensitiveDataRules.isEmpty() || originMessage.isNullOrBlank()) {
            originMessage
            // 获取脱敏后的日志
        } else filterMessage(originMessage)
    }

    /**
     * 处理日志字符串，返回脱敏后的字符串
     *
     * @param message 原始日志字符串
     * @return 脱敏后的字符串
     */
    private fun filterMessage(message: String): String {
        val temp = StringBuilder(message.toLowerCase())
        val source = StringBuilder(message)
        for (sensitiveDataRule in sensitiveDataRules) {
            val key = sensitiveDataRule.fieldName!!.toLowerCase()
            var index = -1
            do {
                index = temp.indexOf(key, index + 1)
                if (index != -1) {
                    // 判断key是否为单词字符
                    if (isWordChar(temp, key, index)) {
                        continue
                    }
                    // 寻找值的开始位置
                    val valueStart = getValueStartIndex(temp, index + key.length)
                    // 查找值的结束位置（逗号，分号）
                    val valueEnd = getValueEndEIndex(temp, valueStart)

                    // 对获取的值进行脱敏
                    val value = temp.substring(valueStart, valueEnd)
                    var replace: String = SensitiveProcessUtils.shield(sensitiveDataRule.format!!, value)
                    if (temp[valueStart] == '{') {
                        continue
                    }
                    if (temp[valueStart - 1] != '"') {
                        replace = "\"" + replace
                    }
                    if (temp[valueEnd] != '"') {
                        replace += "\""
                    }
                    temp.replace(valueStart, valueEnd, replace)
                    source.replace(valueStart, valueEnd, replace)
                }
            } while (index != -1)
        }
        return source.toString()
    }

    /**
     * 判断从 `msg` 中获取的key值是否为单词，
     *
     * @param msg 完整字符串内容
     * @param keyword 检查的关键词
     * @param index 为key在msg中的索引值
     * @return true/false
     */
    private fun isWordChar(msg: CharSequence, keyword: String, index: Int): Boolean {
        // 必须确定key是一个单词
        if (index != 0) {
            // 判断key前面一个字符
            val pre = msg[index - 1]
            if (pre.toInt() in 48..57 || pre.toInt() in 65..90 || pre.toInt() in 97..122) {
                // 0-9 , A-Z, a-z
                return true
            }
        }
        // 判断key后面一个字符
        val next = msg[index + keyword.length]
        // 0-9 , A-Z, a-z
        return next.toInt() in 48..57 || next.toInt() in 65..90 || next.toInt() in 97..122
    }

    /**
     * 获取value值的开始位置
     *
     * @param msg 要查找的字符串
     * @param valueStart 查找的开始位置
     * @return value值的开始位置
     */
    private fun getValueStartIndex(msg: CharSequence, valueStart: Int): Int {
        // 寻找值的开始位置
        var index = valueStart
        do {
            var c = msg[index]
            index++
            if (c == ':' || c == '=' || c == '：') {
                // key与 value的分隔符
                c = msg[index]
                if (c == '"' || c == ' ') {
                    index++
                    c = msg[index]
                    if (c == '"' || c == ' ') {
                        index++
                    }
                }
                // 找到值的开始位置
                break
            }
        } while (true)
        return index
    }

    /**
     * 获取value值的结束位置
     *
     * @param msg 要查找的字符串
     * @param valueEnd 查找的开始位置
     * @return value值的结束位置
     */
    private fun getValueEndEIndex(msg: CharSequence, valueEnd: Int): Int {
        var index = valueEnd
        do {
            if (index == msg.length) {
                break
            }
            val c = msg[index]
            if (c == '"') {
                // 引号时，判断下一个值是结束，分号还是逗号决定是否为值的结束
                if (index + 1 == msg.length) {
                    break
                }
                val next = msg[index + 1]
                if (next == ';' || next == ',' || next == '}' || next == ' ') {
                    break
                } else {
                    index++
                }
            } else if (c == ';' || c == ',' || c == '}') {
                break
            } else {
                index++
            }
        } while (true)
        return index
    }
}
