/**
 * Copyright (c) 2020-2021 Romani Min
 * All rights reserved.
 *
 * SensitiveProcessUtils.kt
 *
 * If you are not the intended user, you are hereby notified that any use, disclosure, copying, printing, forwarding or
 * dissemination of this property is strictly prohibited. If you have got this file in error, delete it from your system.
 */
package tech.chaosmin.framework.utils

import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.LoggerFactory
import org.springframework.util.StringUtils
import tech.chaosmin.framework.base.enums.SensitiveRulesEnum
import java.text.MessageFormat
import java.util.regex.Pattern

/**
 * @author Romani min
 * @since 2021/9/9 10:50
 */
object SensitiveProcessUtils {
    private val logger = LoggerFactory.getLogger(SensitiveProcessUtils::class.java)

    fun shield(sensitiveFormat: SensitiveRulesEnum, info: String): String {
        return if (info.isNotBlank()) {
            var sensitiveInfo = "N.A."
            try {
                val rule = sensitiveFormat.rule
                if (rule.isShow) {
                    sensitiveInfo = when (rule) {
                        SensitiveRulesEnum.RuleEnum.RULE_EMAIL -> {
                            val emailIndex = info.indexOf("@")
                            if (emailIndex == -1) {
                                return info
                            }
                            sensitiveStr(rule, info.substring(0, emailIndex)) + info.substring(emailIndex)
                        }
                        SensitiveRulesEnum.RuleEnum.RULE_HASH -> DigestUtils.md5Hex(info)
                        else -> sensitiveStr(rule, info)
                    }
                }
            } catch (var5: Exception) {
                logger.info("shield catch防异常", var5)
            }
            sensitiveInfo
        } else info
    }

    private fun sensitiveStr(sensitiveFormat: SensitiveRulesEnum.RuleEnum, info: String): String {
        var rule = sensitiveFormat
        val sb = StringBuffer()
        val sensitiveIndex = rule.beforeIndex + rule.afterIndex
        return if (info.isBlank()) info
        else {
            if (info.length <= sensitiveIndex) {
                rule = SensitiveRulesEnum.RuleEnum.RULE_DESENSITIZED_1_
                // sensitiveIndex = rule.beforeIndex + rule.afterIndex
            }
            sb.append(info.substring(0, rule.beforeIndex))
            for (i in 0 until rule.paddingStar) {
                sb.append("*")
            }
            sb.append(info.substring(info.length - rule.afterIndex, info.length))
            sb.toString()
        }
    }

    fun jsonShield(jsonVal: String, fields: Map<String, SensitiveRulesEnum>): String {
        var inString = jsonVal
        return try {
            if (fields.isNotEmpty() && inString.isNotBlank()) {
                inString = if (inString[0] == '{') JsonUtil.encode(JsonUtil.decode(inString, Any::class.java))
                else JsonUtil.encode(JsonUtil.convert2List(inString, Any::class.java))
                val i = fields.keys.iterator()
                while (i.hasNext()) {
                    val fieldName = i.next()
                    val fieldRegex = MessageFormat.format("\\\\*\"({0})\\\\*\":\\\\*\"([^\"\\\\]+)\\\\*\"", fieldName)
                    var sb: StringBuilder?
                    val matcher = Pattern.compile(fieldRegex).matcher(inString)
                    while (matcher.find()) {
                        sb = StringBuilder()
                        sb.append("\"").append(matcher.group(1)).append("\":\"")
                            .append(shield(fields[fieldName]!!, matcher.group(2))).append("\"")
                        inString = StringUtils.replace(inString, matcher.group(0), sb.toString())
                        sb.clear()
                    }
                }
            }
            inString
        } catch (var7: Exception) {
            logger.info("[jsonShield]JSON字符串脱敏异常,注意JSON格式", var7)
            inString
        }
    }

    fun dataShield(srcData: String, fields: Map<String, SensitiveRulesEnum>): String {
        var inString = srcData
        return try {
            if (fields.isNotEmpty() && inString.isNotBlank()) {
                val i = fields.keys.iterator()
                while (i.hasNext()) {
                    val fieldName = i.next()
                    var matcher = Pattern.compile("([^,\\s\\d-\\[\\]&?{}][\\s*\\w]+?)=([^,\"\\*\\[\\]{}<]\\s*\\w+)").matcher(inString)
                    while (matcher.find()) {
                        if (matcher.group(1).trim { it <= ' ' } == fieldName) {
                            val sb = StringBuilder()
                            sb.append(matcher.group(1)).append("=").append(shield(fields[fieldName]!!, matcher.group(2)))
                            inString = StringUtils.replace(inString, matcher.group(0), sb.toString())
                            sb.clear()
                        }
                    }
                    val fieldRegex = MessageFormat.format("\\\\*\"({0})\\\\*\":\\\\*\"([^\"\\\\]+)\\\\*\"", fieldName)
                    var sb: StringBuilder?
                    matcher = Pattern.compile(fieldRegex).matcher(inString)
                    while (matcher.find()) {
                        sb = StringBuilder()
                        sb.append("\"").append(matcher.group(1)).append("\":\"")
                            .append(shield(fields[fieldName]!!, matcher.group(2))).append("\"")
                        inString = StringUtils.replace(inString, matcher.group(0), sb.toString())
                        sb.clear()
                    }
                }
            }
            inString
        } catch (var7: Exception) {
            logger.info("[dataShield]字符串脱敏异常,注意", var7)
            inString
        }
    }
}